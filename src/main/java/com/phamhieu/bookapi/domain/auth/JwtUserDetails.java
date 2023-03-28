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

    public JwtUserDetails(final com.phamhieu.bookapi.domain.user.User user,
                          final Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.userId = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.avatar = user.getAvatar();
    }
}