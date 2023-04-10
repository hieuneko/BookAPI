package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.persistence.role.RoleStore;
import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.String;
import java.util.Collection;
import java.util.UUID;

import static java.util.Collections.singleton;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

    private final GoogleTokenVerifierService googleTokenVerifierService;

    private final UserStore userStore;

    private final RoleStore roleStore;

    public UserDetails loginGoogle(final String decodedToken) {
        final GoogleTokenPayload googleAccount = googleTokenVerifierService.googleIdTokenVerifier(decodedToken);
        return userStore.findByUsername(googleAccount.getEmail())
                .map(this::getJwtUserDetails)
                .orElseGet(() -> createNewGoogleUser(googleAccount));
    }

    private JwtUserDetails getJwtUserDetails(User user) {
        final String roleName = roleStore.findRoleName(user.getRoleId());
        return new JwtUserDetails(user, createAuthorities(roleName));
    }

    private JwtUserDetails createNewGoogleUser(GoogleTokenPayload googleAccount) {
        final UUID roleId = roleStore.findIdByName("CONTRIBUTOR");
        final User newUser = createUser(googleAccount, roleId);
        return new JwtUserDetails(newUser, createAuthorities("CONTRIBUTOR"));
    }

    private User createUser(GoogleTokenPayload googleAccount, UUID roleId) {
        final User newUser = User.builder()
                .username(googleAccount.getEmail())
                .password(UUID.randomUUID().toString())
                .enabled(true)
                .roleId(roleId)
                .build();
        return userStore.create(newUser);
    }

    private Collection<? extends GrantedAuthority> createAuthorities(final String role) {
        return singleton(new SimpleGrantedAuthority(role));
    }
}
