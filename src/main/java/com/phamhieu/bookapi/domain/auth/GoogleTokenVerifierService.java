package com.phamhieu.bookapi.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.phamhieu.bookapi.configuration.GoogleTokenVerifierConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.phamhieu.bookapi.domain.auth.GoogleTokenPayloadMapper.toGoogleTokenPayload;

@Service
@RequiredArgsConstructor
public class GoogleTokenVerifierService {

    private final GoogleTokenVerifierConfig googleTokenVerifierConfig;

    public GoogleTokenPayload googleIdTokenVerifier(final String idToken) {
        try {

            final var googleIdToken = googleTokenVerifierConfig.tokenVerify().verify(idToken);
            final GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return toGoogleTokenPayload(payload);

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
