package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.persistence.role.RoleStore;
import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.String;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

    private final GoogleTokenVerifierService googleTokenVerifierService;

    private final UserStore userStore;

    private final RoleStore roleStore;

    private final JwtUserDetailsService jwtUserDetailsService;

    public UserDetails loginGoogle(final String decodedToken) {
        final GoogleTokenPayload googleAccount = googleTokenVerifierService.googleIdTokenVerifier(decodedToken);
        final Optional<User> existUser = userStore.findByUsername(googleAccount.getEmail());

        if (existUser.isEmpty()) {
            final UUID roleId = roleStore.findIdByName("CONTRIBUTOR");
            final User newUser = User.builder()
                    .username(googleAccount.getEmail())
                    .password(UUID.randomUUID().toString())
                    .enabled(true)
                    .roleId(roleId)
                    .build();
            userStore.create(newUser);

            return jwtUserDetailsService.loadUserByUsername(newUser.getUsername());
        }
        return jwtUserDetailsService.loadUserByUsername(existUser.get().getUsername());
    }
}
