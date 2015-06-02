package nz.co.xingsoft.memribox.server.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

    private static final String MAGIC_KEY = "obfuscate";

    @Value("${security.token.expire.time:3600000}")
    private Long tokenExpireTime;

    public String createToken(final UserDetails userDetails) {

        /* Expires in one hour */
        final long expires = System.currentTimeMillis() + tokenExpireTime;

        final StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append(userDetails.getUsername());
        tokenBuilder.append(":");
        tokenBuilder.append(expires);
        tokenBuilder.append(":");
        tokenBuilder.append(computeSignature(userDetails, expires));

        return tokenBuilder.toString();
    }

    public String computeSignature(final UserDetails userDetails, final long expires) {

        final StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername());
        signatureBuilder.append(":");
        signatureBuilder.append(expires);
        signatureBuilder.append(":");
        signatureBuilder.append(userDetails.getPassword());
        signatureBuilder.append(":");
        signatureBuilder.append(TokenUtils.MAGIC_KEY);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }

    public String getUserNameFromToken(final String authToken) {

        if (StringUtils.isBlank(authToken)) {
            return null;
        }

        final String[] parts = authToken.split(":");
        return parts[0];
    }

    public boolean validateToken(final String authToken, final UserDetails userDetails) {

        final String[] parts = authToken.split(":");
        final long expires = Long.parseLong(parts[1]);
        final String signature = parts[2];

        if (expires < System.currentTimeMillis()) {
            return false;
        }

        return signature.equals(computeSignature(userDetails, expires));
    }
}
