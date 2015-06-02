package nz.co.xingsoft.memribox.server.security.util;

import static nz.co.xingsoft.memribox.server.TestConstants.TEST_HASHED_PASSWORD;
import static nz.co.xingsoft.memribox.server.TestConstants.TEST_PASSWORD;
import static org.fest.assertions.Assertions.assertThat;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class SaltedSHA256PasswordEncoderTest {

    @Test
    public void testEncode()
            throws NoSuchAlgorithmException {
        final SaltedSHA256PasswordEncoder encoder = new SaltedSHA256PasswordEncoder();
        encoder.setSalt("$s3cr3t");

        assertThat(encoder.encode(TEST_PASSWORD)).isEqualTo(TEST_HASHED_PASSWORD);

    }
}
