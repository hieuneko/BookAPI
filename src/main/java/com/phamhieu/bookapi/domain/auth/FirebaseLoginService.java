package com.phamhieu.bookapi.domain.auth;

import com.google.firebase.auth.FirebaseAuthException;
import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.persistence.role.RoleStore;
import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.String;
import java.util.Optional;
import java.util.UUID;

import static com.phamhieu.bookapi.domain.auth.AuthError.supplyProjectIdIncorrect;

@Service
@RequiredArgsConstructor
public class FirebaseLoginService {

    @Value("${firebase.project.id}")
    private String firebaseProjectId;

    private final UserStore userStore;

    private final RoleStore roleStore;

    private final JwtUserDetailsService jwtUserDetailsService;

    private final FirebaseTokenVerifierService firebaseTokenVerifierService;

    public UserDetails loginGoogle(final String decodedToken) throws FirebaseAuthException {
        final FirebaseTokenPayload firebaseToken = firebaseTokenVerifierService.verifyToken(decodedToken);
        verifyProjectId(firebaseToken.getProjectId());

        final Optional<User> existUser = userStore.findByUsername(firebaseToken.getEmail());

        if (existUser.isEmpty()) {
            final UUID roleId = roleStore.findIdByName("CONTRIBUTOR");
            final User newUser = User.builder()
                    .username(firebaseToken.getEmail())
                    .password(UUID.randomUUID().toString())
                    .enabled(true)
                    .roleId(roleId)
                    .build();
            userStore.create(newUser);

            return jwtUserDetailsService.loadUserByUsername(newUser.getUsername());
        }

        return jwtUserDetailsService.loadUserByUsername(existUser.get().getUsername());
    }

    private void verifyProjectId(final String projectId) {
        if (!projectId.equals(firebaseProjectId)) {
            throw supplyProjectIdIncorrect().get();
        }
    }
}
