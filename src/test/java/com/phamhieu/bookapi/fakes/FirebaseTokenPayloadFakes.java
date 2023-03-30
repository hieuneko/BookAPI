package com.phamhieu.bookapi.fakes;

import com.phamhieu.bookapi.domain.auth.FirebaseTokenPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FirebaseTokenPayloadFakes {

    public static FirebaseTokenPayload buildToken() {
        return FirebaseTokenPayload.builder()
                .projectId("book-api-2b55e")
                .email("test")
                .build();
    }
}
