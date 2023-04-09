package com.phamhieu.bookapi.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.phamhieu.bookapi.domain.auth.GoogleTokenPayloadMapper.toGoogleTokenPayload;

@Service
public class GoogleTokenVerifierService {

    private final GoogleIdTokenVerifier tokenVerifier;

    @Autowired
    public GoogleTokenVerifierService(GoogleIdTokenVerifier tokenVerifier) {
        this.tokenVerifier = tokenVerifier;
    }

    public GoogleTokenPayload googleIdTokenVerifier(final String idToken) {
        try {
            final var googleIdToken = tokenVerifier.verify(idToken);
            final GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return toGoogleTokenPayload(payload);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
