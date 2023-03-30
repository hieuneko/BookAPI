package com.phamhieu.bookapi.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyFirebaseToken {

    private String projectId;
    private String email;
}
