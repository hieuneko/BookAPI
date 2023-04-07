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
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

    private final GoogleTokenVerifierService googleTokenVerifierService;

    private final UserStore userStore;

    private final RoleStore roleStore;

    public UserDetails loginGoogle(final String decodedToken) {
        final GoogleTokenPayload googleAccount = googleTokenVerifierService.googleIdTokenVerifier(decodedToken);
        final Collection<? extends GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("CONTRIBUTOR"));
        return userStore.findByUsername(googleAccount.getEmail())
                .map(user -> new JwtUserDetails(user, authorities))
                .orElseGet(() -> createNewGoogleUser(googleAccount, authorities));
    }

    private JwtUserDetails createNewGoogleUser(GoogleTokenPayload googleAccount, Collection<? extends GrantedAuthority> authorities) {
        final UUID roleId = roleStore.findIdByName("CONTRIBUTOR");
        final User newUser = User.builder()
                .username(googleAccount.getEmail())
                .password(UUID.randomUUID().toString())
                .enabled(true)
                .roleId(roleId)
                .build();
        userStore.create(newUser);
        return new JwtUserDetails(newUser, authorities);
    }
}
