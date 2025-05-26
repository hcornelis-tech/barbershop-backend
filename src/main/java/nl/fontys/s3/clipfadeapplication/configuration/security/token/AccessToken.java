package nl.fontys.s3.clipfadeapplication.configuration.security.token;

import java.util.Set;

public interface AccessToken {
    String getUserEmail();

    int getUserId();

    Set<String> getRoles();

    boolean hasRole(String role);
}
