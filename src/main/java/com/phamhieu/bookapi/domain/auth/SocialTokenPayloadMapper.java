package com.phamhieu.bookapi.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.experimental.UtilityClass;
import org.springframework.social.facebook.api.User;

@UtilityClass
public class SocialTokenPayloadMapper {

    public static SocialTokenPayload toSocialTokenPayload(final GoogleIdToken.Payload payload) {
        return SocialTokenPayload.builder()
                .email(payload.getEmail())
                .username((String) payload.get("name"))
                .firstName((String) payload.get("family_name"))
                .lastName((String) payload.get("given_name"))
                .build();
    }

    public static SocialTokenPayload toSocialTokenPayload(final User user) {
        return SocialTokenPayload.builder()
                .email(user.getEmail())
                .username(user.getId())
                .name(user.getName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
