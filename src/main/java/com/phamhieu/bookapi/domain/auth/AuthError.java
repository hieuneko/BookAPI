package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.error.DomainException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@UtilityClass
public class AuthError {

    public static Supplier<DomainException> supplyAuthorizationAccessNeeded() {
        return () -> new DomainException(HttpStatus.FORBIDDEN, "You must be admin or the book's creator to be able to do action");
    }
}
