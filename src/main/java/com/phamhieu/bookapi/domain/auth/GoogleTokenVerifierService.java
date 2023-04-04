package com.phamhieu.bookapi.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleTokenVerifierService {

    private static final String CLIENT_ID="117223285300-7v6i7cfgmlgalskig5s1ukinggd1cnn1.apps.googleusercontent.com";

    public GoogleTokenPayload googleIdTokenVerifier(final String idToken) {
        final var verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            final var googleIdToken = verifier.verify(idToken);
            final GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return GoogleTokenPayload.builder()
                    .email(payload.getEmail())
                    .firstName((String) payload.get("family_name"))
                    .lastName((String) payload.get("given_name"))
                    .avatar((String) payload.get("picture"))
                    .build();

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
