package com.phamhieu.bookapi.fakes;

import com.phamhieu.bookapi.domain.auth.GoogleTokenPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GoogleTokenPayloadFakes {

    public static GoogleTokenPayload buildToken() {
        return GoogleTokenPayload.builder()
                .email("test@gmail.com")
                .firstName("test")
                .lastName("test")
                .avatar("avatar.png")
                .build();
    }
}
