package com.phamhieu.bookapi.fakes;

import com.phamhieu.bookapi.domain.auth.SocialTokenPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SocialTokenPayloadFakes {

    public static SocialTokenPayload buildToken() {
        return SocialTokenPayload.builder()
                .email("test@gmail.com")
                .name("name")
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .build();
    }
}
