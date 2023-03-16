package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.error.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthProvider {

    public UserAuthenticationToken getCurrentAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new UnauthorizedException();
        }

        return (UserAuthenticationToken) authentication;
    }
}
