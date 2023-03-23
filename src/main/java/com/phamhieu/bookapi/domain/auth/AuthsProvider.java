package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.error.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthsProvider {

    public UserAuthenticationToken getCurrentAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new UnauthorizedException();
        }

        return (UserAuthenticationToken) authentication;
    }

    public UUID getCurrentUserId() {
        return getCurrentAuthentication().getUserId();
    }

    public String getCurrentUsername() {
        return getCurrentAuthentication().getUsername();
    }

    public String getCurrentRole() {
        return getCurrentAuthentication().getRole();
    }

    public String getCurrentFirstName() {
        return getCurrentAuthentication().getFistName();
    }

    public String getCurrentLastName() {
        return getCurrentAuthentication().getLastName();
    }

    public String getCurrentAvatar() {
        return getCurrentAuthentication().getAvatar();
    }
}
