package nz.co.xingsoft.memribox.server.ws.resources;

import static nz.co.xingsoft.memribox.server.common.ResourcesConstants.REST_PATH_AUTH;
import static nz.co.xingsoft.memribox.server.common.ResourcesConstants.REST_PATH_REGISTER;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import nz.co.xingsoft.memribox.server.business.dto.AuthToken;
import nz.co.xingsoft.memribox.server.business.dto.RegistrationRequest;
import nz.co.xingsoft.memribox.server.business.dto.UserDetailsDto;
import nz.co.xingsoft.memribox.server.business.services.UserService;
import nz.co.xingsoft.memribox.server.security.util.TokenUtils;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.JResponse;

@Path("/user")
@Service
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserService userService;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private TokenUtils tokenUtils;

    @Inject
    private DozerBeanMapper beanMapper;

    @Inject
    private ErrorHandlerResource errorHandlerResource;

    /**
     * Retrieves the currently logged in user.
     */
    // @Path("current")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserDetailsDto getUser() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        final UserDetails userDetails = (UserDetails) principal;

        return beanMapper.map(userDetails, UserDetailsDto.class);
    }

    @Path(REST_PATH_REGISTER)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JResponse<?> register(final RegistrationRequest request) {
        try {
            userService.regsiter(request);
            return JResponse.status(Status.OK).entity(String.format("Register user %s successfully", request.getUsername())).build();
        } catch (final Exception e) {
            return errorHandlerResource.processException("register", e);
        }

    }

    @Path(REST_PATH_AUTH)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public JResponse<?> authenticate(@FormParam("username") final String username, @FormParam("password") final String password) {

        try {
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            final Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            /*
             * Reload user as password of authentication principal will be null after authorization and password is needed for token
             * generation
             */
            final UserDetails userDetails = this.userService.loadUserByUsername(username);

            return JResponse.status(Status.OK).entity(new AuthToken(tokenUtils.createToken(userDetails))).build();

        } catch (final Exception e) {
            LOGGER.error("Authenticate user '{}' failed: either username or password is incorrect.", username, e);
            return JResponse.status(Status.UNAUTHORIZED).entity("The username or password is incorrect.").build();
        }
    }
}
