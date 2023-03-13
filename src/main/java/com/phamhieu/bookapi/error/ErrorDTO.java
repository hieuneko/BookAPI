package com.phamhieu.bookapi.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.springframework.util.Assert.notNull;

@Builder
@Getter
@AllArgsConstructor
public class ErrorDTO {

    private String message;
    private Instant occurAt;
}
