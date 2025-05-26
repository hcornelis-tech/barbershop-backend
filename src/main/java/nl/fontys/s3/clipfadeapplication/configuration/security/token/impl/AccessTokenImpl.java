package nl.fontys.s3.clipfadeapplication.configuration.security.token.impl;

import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessToken;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String userEmail;
    private final int userId;
    private final Set<String> roles;

    public AccessTokenImpl(String userEmail, int userId, Collection<String> roles) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
    }

    @Override
    public boolean hasRole(String role) {
        return roles.contains(role);
    }
}
