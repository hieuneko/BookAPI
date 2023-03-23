package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.error.ForbiddenException;
import com.phamhieu.bookapi.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class AuthError {

    public static Supplier<ForbiddenException> supplyBookAccessDenied() {
        return () -> new ForbiddenException("You must be admin or the book's creator to be able to do action");
    }

    public static Supplier<NotFoundException> supplyUsernameNotFound(final String username) {
        return () -> new NotFoundException("User has username: %s not found", username);
    }
}
