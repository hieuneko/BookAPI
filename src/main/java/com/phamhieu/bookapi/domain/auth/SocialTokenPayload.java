package com.phamhieu.bookapi.domain.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialTokenPayload {

    private String email;
    private String username;
    private String name;
    private String firstName;
    private String lastName;
}
