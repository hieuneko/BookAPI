package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.persistence.user.UserEntity;
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

    public JwtUserDetails(final UserEntity userEntity,
                          final Collection<? extends GrantedAuthority> authorities) {
        super(userEntity.getUsername(), userEntity.getPassword(), authorities);
        this.userId = userEntity.getId();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.avatar = userEntity.getAvatar();
    }
}