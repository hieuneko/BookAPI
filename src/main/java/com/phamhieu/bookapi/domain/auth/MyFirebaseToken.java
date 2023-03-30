package com.phamhieu.bookapi.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyFirebaseToken {

    private String projectId;
    private String email;
}
