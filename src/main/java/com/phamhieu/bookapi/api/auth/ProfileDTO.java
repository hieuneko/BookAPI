package com.phamhieu.bookapi.api.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProfileDTO {

    private UUID userId;
    private String username;
    private String role;
}
