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
                .map(user -> {
                    final String roleName = roleStore.findRoleName(user.getRoleId());
                    return new JwtUserDetails(user, createGoogleUserAuthorities(roleName));
                })
                .orElseGet(() -> createNewGoogleUser(googleAccount));
    }

    private JwtUserDetails createNewGoogleUser(GoogleTokenPayload googleAccount) {
        final String newGoogleUserRole = "CONTRIBUTOR";
        final UUID roleId = roleStore.findIdByName(newGoogleUserRole);
        final User newUser = User.builder()
                .username(googleAccount.getEmail())
                .password(UUID.randomUUID().toString())
                .enabled(true)
                .roleId(roleId)
                .build();
        userStore.create(newUser);
        return new JwtUserDetails(newUser, createGoogleUserAuthorities(newGoogleUserRole));
    }

    private Collection<? extends GrantedAuthority> createGoogleUserAuthorities(final String role) {
        return singleton(new SimpleGrantedAuthority(role));
    }
}
