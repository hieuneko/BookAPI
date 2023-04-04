package com.phamhieu.bookapi.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class GoogleTokenPayload {

    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
}
