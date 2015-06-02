package nz.co.xingsoft.memribox.server.security;

import static nz.co.xingsoft.memribox.server.TestConstants.TEST_PASSWORD;
import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.SpringApplicationContextTest;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationManagerTest
        extends SpringApplicationContextTest {

    @Inject
    private AuthenticationManager authenticationManager;

    @Test
    @Ignore
    public void testAuthenticate() {

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("d@d.com", TEST_PASSWORD);

        final Authentication authentication = authenticationManager.authenticate(authenticationToken);

        assertThat(authentication.getAuthorities()).hasSize(2);

    }

    @Test(expected = BadCredentialsException.class)
    public void testInvalidPassword() {

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("d@d.com", "dummy");

        authenticationManager.authenticate(authenticationToken);
    }

    @Test(expected = AuthenticationException.class)
    public void testInvalidUsername1() {

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("dummy", "dummy");

        authenticationManager.authenticate(authenticationToken);
    }

    @Test(expected = AuthenticationException.class)
    public void testInvalidUsername2() {

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null, "dummy");

        authenticationManager.authenticate(authenticationToken);
    }
}
