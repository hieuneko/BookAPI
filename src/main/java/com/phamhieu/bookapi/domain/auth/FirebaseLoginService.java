package com.phamhieu.bookapi.domain.auth;

import com.google.firebase.auth.FirebaseAuthException;
import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.String;

import static com.phamhieu.bookapi.domain.auth.AuthError.supplyProjectIdIncorrect;
import static com.phamhieu.bookapi.domain.user.UserError.supplyUserGoogleNotInSystem;

@Service
@RequiredArgsConstructor
public class FirebaseLoginService {

    @Value("${firebase.project.id}")
    private String firebaseProjectId;

    private final UserStore userStore;

    private final JwtUserDetailsService jwtUserDetailsService;

    private final FirebaseTokenVerifierService firebaseTokenVerifierService;

    public UserDetails loginGoogle(final String decodedToken) throws FirebaseAuthException {
        final MyFirebaseToken firebaseToken = firebaseTokenVerifierService.verifyToken(decodedToken);
        verifyProjectId(firebaseToken.getProjectId());

        final User existUser = userStore.findByUsername(firebaseToken.getEmail())
                .orElseThrow(supplyUserGoogleNotInSystem(firebaseToken.getEmail()));

        return jwtUserDetailsService.loadUserByUsername(existUser.getUsername());
    }

    private void verifyProjectId(final String projectId) {
        if (!projectId.equals(firebaseProjectId)) {
            throw supplyProjectIdIncorrect().get();
        }
    }
}
