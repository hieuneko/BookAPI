package com.phamhieu.bookapi.domain.auth;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Getter
public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final UUID userId;
    private final String username;
    private final String fistName;
    private final String lastName;
    private final String avatar;
    private final String role;

    public UserAuthenticationToken(final UUID userId, final String username, final String fistName,
                                   final String lastName, final String avatar,
                                   final Collection<? extends GrantedAuthority> authorities) {
        super(userId, username, authorities);
        this.userId = userId;
        this.username = username;
        this.fistName = fistName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.role = this.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(null);
    }
}
