package com.phamhieu.bookapi.domain.auth;

import lombok.experimental.UtilityClass;

import java.util.UUID;

import static com.phamhieu.bookapi.domain.auth.AuthError.supplyAuthorizationAccessNeeded;

@UtilityClass
public class AuthValidation {

    public void validateAuth(final AuthsProvider authsProvider, final UUID userId) {
        if (!authsProvider.getCurrentAuthentication().getRole().equals("ADMIN") ||
                !(authsProvider.getCurrentAuthentication().getUserId() == userId)) {
            throw supplyAuthorizationAccessNeeded().get();
        }
    }
}
