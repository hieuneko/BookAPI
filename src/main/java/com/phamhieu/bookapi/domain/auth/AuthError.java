package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.error.BadRequestException;
import com.phamhieu.bookapi.error.ForbiddenException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class AuthError {

    public static Supplier<ForbiddenException> supplyBookAccessDenied() {
        return () -> new ForbiddenException("You must be admin or the book's creator to be able to do action");
    }

    public static Supplier<BadRequestException> supplyProjectIdIncorrect() {
        return () -> new BadRequestException("Your Google account not belong to this website");
    }
}
