package nz.co.xingsoft.memribox.server.security.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Simple password encoder that uses a salt value and encodes the password with SHA-256.
 */
@Component
public class SaltedSHA256PasswordEncoder
        implements PasswordEncoder {

    @Value("${sha256.salt:$s3cr3t}")
    private String salt;

    private final MessageDigest digest;

    public SaltedSHA256PasswordEncoder() throws NoSuchAlgorithmException {

        this.digest = MessageDigest.getInstance("SHA-256");
    }

    @Override
    public String encode(final CharSequence rawPassword) {

        final String saltedPassword = rawPassword + this.salt;
        try {
            return new String(Hex.encode(this.digest.digest(saltedPassword.getBytes("UTF-8"))));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported");
        }
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {

        return this.encode(rawPassword).equals(encodedPassword);
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

}
