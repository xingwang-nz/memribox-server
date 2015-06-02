package nz.co.xingsoft.memribox.server.business.services;

import nz.co.xingsoft.memribox.server.business.dto.RegistrationRequest;
import nz.co.xingsoft.memribox.server.business.exception.RegistrationException;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService
        extends UserDetailsService {

    public void regsiter(final RegistrationRequest request)
            throws RegistrationException;

}
