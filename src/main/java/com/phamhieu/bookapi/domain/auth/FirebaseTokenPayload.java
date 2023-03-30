package com.phamhieu.bookapi.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FirebaseTokenPayload {

    private String projectId;
    private String email;
}
