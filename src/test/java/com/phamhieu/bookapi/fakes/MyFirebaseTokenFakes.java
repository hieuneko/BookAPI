package com.phamhieu.bookapi.fakes;

import com.phamhieu.bookapi.domain.auth.MyFirebaseToken;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MyFirebaseTokenFakes {

    public static MyFirebaseToken buildToken() {
        return MyFirebaseToken.builder()
                .projectId("book-api-2b55e")
                .email("test")
                .build();
    }
}
