package com.phamhieu.bookapi.fakes;

import com.phamhieu.bookapi.domain.auth.JwtUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;

@UtilityClass
public class AuthenticationFakes {

    public static Authentication buildAuthentication() {
        final var user = buildUser();
        final JwtUserDetails userDetails = new JwtUserDetails(
                user,
                Collections.emptyList()
        );
        return new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(), userDetails.getAuthorities());
    }
}
