package com.phamhieu.bookapi.api.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtTokenResponseDTO {

    private String token;
}
