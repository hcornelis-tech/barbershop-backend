package nl.fontys.s3.clipfadeapplication.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
