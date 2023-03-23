package com.phamhieu.bookapi.fakes;

import com.phamhieu.bookapi.domain.auth.UserAuthenticationToken;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class UserAuthenticationTokenFakes {

    public static UserAuthenticationToken buildAdmin() {
        return new UserAuthenticationToken(
                UUID.fromString("10c524a3-3225-4817-8277-f73578d36a05"),
                "admin",
                "test",
                "test",
                "avatar.png",
                List.of(new SimpleGrantedAuthority("ADMIN")));
    }

    public static UserAuthenticationToken buildContributor() {
        return new UserAuthenticationToken(
                UUID.fromString("52aed357-9a77-432c-80b6-7bfdc34d46e7"),
                "user",
                "test",
                "test",
                "avatar.png",
                List.of(new SimpleGrantedAuthority("CONTRIBUTOR")));
    }
}
