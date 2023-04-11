package com.phamhieu.bookapi.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.phamhieu.bookapi.domain.auth.SocialTokenPayloadMapper.toSocialTokenPayloadFromGoogle;

@Service
@RequiredArgsConstructor
public class GoogleTokenVerifierService {

    private final GoogleIdTokenVerifier tokenVerifier;

    public SocialTokenPayload verifyGoogleIdToken(final String idToken) {
        try {
            final var googleIdToken = tokenVerifier.verify(idToken);
            final GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return toSocialTokenPayloadFromGoogle(payload);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
