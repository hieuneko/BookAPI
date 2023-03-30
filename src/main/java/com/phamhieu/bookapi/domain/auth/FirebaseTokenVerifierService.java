package com.phamhieu.bookapi.domain.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseTokenVerifierService {

    public MyFirebaseToken verifyToken(final String token) throws FirebaseAuthException {
        final FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
        final String projectId = firebaseToken.getClaims().get("aud").toString();
        final String email = firebaseToken.getEmail();
        return new MyFirebaseToken(projectId, email);
    }
}
