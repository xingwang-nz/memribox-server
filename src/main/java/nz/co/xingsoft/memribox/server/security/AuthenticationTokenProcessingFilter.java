package nz.co.xingsoft.memribox.server.security;

import static nz.co.xingsoft.memribox.server.common.ResourcesConstants.HEADER_AUTH;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import nz.co.xingsoft.memribox.server.business.services.UserService;
import nz.co.xingsoft.memribox.server.common.ResourcesConstants;
import nz.co.xingsoft.memribox.server.security.util.TokenUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class AuthenticationTokenProcessingFilter
        extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenProcessingFilter.class);

    @Inject
    private UserService userService;

    @Inject
    private TokenUtils tokenUtils;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest httpRequest = this.getAsHttpRequest(request);

        final String authToken = this.extractAuthTokenFromRequest(httpRequest);
        final String userName = tokenUtils.getUserNameFromToken(authToken);

        if (StringUtils.isNotBlank(userName)) {

            try {
                final UserDetails userDetails = this.userService.loadUserByUsername(userName);

                if (tokenUtils.validateToken(authToken, userDetails)) {

                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

                    SecurityContextHolder.getContext().setAuthentication(authentication); 
                }

            } catch (final Exception e) {
                LOGGER.error("Process token {} failed", authToken, e);
            }
        }

        chain.doFilter(request, response);
    }

    private HttpServletRequest getAsHttpRequest(final ServletRequest request) {

        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }

        return (HttpServletRequest) request;
    }

    private String extractAuthTokenFromRequest(final HttpServletRequest httpRequest) {

        /* Get token from header, otherwise get it from request parameter */
        final String authToken = httpRequest.getHeader(HEADER_AUTH);

        return authToken != null ? authToken : httpRequest.getParameter(ResourcesConstants.REQUEST_TOKEN);

    }
}
