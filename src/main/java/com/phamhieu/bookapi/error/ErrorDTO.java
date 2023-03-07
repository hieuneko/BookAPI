package com.phamhieu.bookapi.error;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.springframework.util.Assert.notNull;

@Builder
@Getter
public class ErrorDTO {

    private String message;
    private Instant occurAt;

    ErrorDTO(final String message, final Instant occurAt) {
        notNull(message, "Cannot be null.");
        notEmpty(message, "Cannot be empty");

        this.message = message;
        this.occurAt = occurAt;
    }
}
