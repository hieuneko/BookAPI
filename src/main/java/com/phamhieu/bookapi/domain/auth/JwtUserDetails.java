package com.phamhieu.bookapi.domain.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
public class JwtUserDetails extends User {

    private final UUID userId;
    private final String firstName;
    private final String lastName;
    private final String avatar;

    public JwtUserDetails(final UUID userId,
                          final String username,
                          final String password,
                          final String firstName,
                          final String lastName,
                          final String avatar,
                          final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }
}