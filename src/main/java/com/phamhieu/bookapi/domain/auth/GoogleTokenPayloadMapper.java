package com.phamhieu.bookapi.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GoogleTokenPayloadMapper {

    public static GoogleTokenPayload toGoogleTokenPayload(final GoogleIdToken.Payload payload) {
        return GoogleTokenPayload.builder()
                .email(payload.getEmail())
                .firstName((String) payload.get("family_name"))
                .lastName((String) payload.get("given_name"))
                .avatar((String) payload.get("picture"))
                .build();
    }
}
