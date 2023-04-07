package com.phamhieu.bookapi.domain.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleTokenPayload {

    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
}
