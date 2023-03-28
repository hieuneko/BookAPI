package com.phamhieu.bookapi.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
@AllArgsConstructor
public class ErrorDTO {

    private String message;
    private Instant occurAt;
}
