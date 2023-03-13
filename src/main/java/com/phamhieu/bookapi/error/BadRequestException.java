package com.phamhieu.bookapi.error;

import org.springframework.http.HttpStatus;

public class BadRequestException extends DomainException {

    public BadRequestException(final String message, Object... args) {
        super(HttpStatus.BAD_REQUEST, message, args);
    }
}
