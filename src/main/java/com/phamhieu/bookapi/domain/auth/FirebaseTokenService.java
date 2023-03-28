package com.phamhieu.bookapi.domain.auth;

import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.phamhieu.bookapi.domain.auth.AuthError.supplyProjectIdIncorrect;

@Service
@RequiredArgsConstructor
public class FirebaseTokenService {

    @Value("${firebase.project.id}")
    private final String firebaseProjectId;

    public JwtUserDetails loginGoogle(final FirebaseToken firebaseToken) {
        final String projectId = firebaseToken.getClaims().get("aud").toString();
        verifyProjectId(projectId);

        final String email = firebaseToken.getEmail();
        final String name = firebaseToken.getName();
        final String avatar = firebaseToken.getPicture();
        final String userId = firebaseToken.getUid();
    }

    private void verifyProjectId(final String projectId) {
        if (!projectId.equals(firebaseProjectId)) {
            throw supplyProjectIdIncorrect().get();
        }
    }
}
