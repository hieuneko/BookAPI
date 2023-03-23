package com.phamhieu.bookapi.error;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends DomainException{

    public ForbiddenException(final String message, Object... args) {
        super(HttpStatus.FORBIDDEN, message, args);
    }
}
