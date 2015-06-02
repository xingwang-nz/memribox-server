package nz.co.xingsoft.memribox.server.business.services;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import nz.co.xingsoft.memribox.server.SpringApplicationContextTest;
import nz.co.xingsoft.memribox.server.business.dto.RegistrationRequest;
import nz.co.xingsoft.memribox.server.business.exception.RegistrationException;
import nz.co.xingsoft.memribox.server.business.services.UserService;

import org.junit.Test;

public class UserServiceTest
        extends SpringApplicationContextTest {

    @Inject
    private UserService userService;

    @Test(expected = ConstraintViolationException.class)
    public void testRegister()
            throws RegistrationException {
        final RegistrationRequest request = new RegistrationRequest();

        request.setUsername("xing");
        request.setPassword("12345");
        request.setFirstName("Xing");
        request.setLastName("Wang");
        request.setEmail("ss@ss.com");

        userService.regsiter(request);

    }

    @Test(expected = ConstraintViolationException.class)
    public void testRegisterWithInvaliadEmail()
            throws RegistrationException {
        final RegistrationRequest request = new RegistrationRequest();

        request.setUsername("xing");
        request.setPassword("12345");
        request.setFirstName("Xing");
        request.setLastName("Wang");
        request.setEmail("ss@");

        userService.regsiter(request);

    }

}
