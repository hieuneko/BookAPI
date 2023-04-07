package com.phamhieu.bookapi.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.phamhieu.bookapi.configuration.GoogleTokenVerifierConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GoogleTokenVerifierService {

    private final GoogleTokenVerifierConfig googleTokenVerifierConfig;

    public GoogleTokenPayload googleIdTokenVerifier(final String idToken) {
        try {

            final var googleIdToken = googleTokenVerifierConfig.tokenVerify().verify(idToken);
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
