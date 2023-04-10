package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.persistence.role.RoleStore;
import com.phamhieu.bookapi.persistence.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.phamhieu.bookapi.fakes.GoogleTokenPayloadFakes.buildToken;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoogleLoginServiceTest {

    @Mock
    private UserStore userStore;

    @Mock
    private GoogleTokenVerifierService googleTokenVerifierService;

    @Mock
    private RoleStore roleStore;


    @InjectMocks
    private GoogleLoginService googleLoginService;


    @Test
    void shouldLoginGoogle_OK() {
        final GoogleTokenPayload tokenPayload = buildToken();
        final var user = buildUser();
        final var authorities = randomAlphabetic(3,10);

        user.setUsername(tokenPayload.getEmail());

        final JwtUserDetails userDetails = new JwtUserDetails(user, List.of(new SimpleGrantedAuthority(authorities)));

        when(googleTokenVerifierService.googleIdTokenVerifier(anyString())).thenReturn(tokenPayload);
        when(userStore.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(roleStore.findRoleName(user.getRoleId())).thenReturn(authorities);

        final var actual = googleLoginService.loginGoogle(anyString());

        assertEquals(userDetails, actual);

        verify(googleTokenVerifierService).googleIdTokenVerifier(anyString());
        verify(userStore).findByUsername(user.getUsername());
        verify(roleStore).findRoleName(user.getRoleId());
    }

    @Test
    void shouldLoginGoogle_CreateNewUser() {
        final GoogleTokenPayload tokenPayload = buildToken();

        when(googleTokenVerifierService.googleIdTokenVerifier(anyString())).thenReturn(tokenPayload);
        when(userStore.findByUsername(tokenPayload.getEmail())).thenReturn(Optional.empty());

        final var uid = UUID.randomUUID();

        when(roleStore.findIdByName(anyString())).thenReturn(uid);

        final User newUser = User.builder()
                .username(tokenPayload.getEmail())
                .password(UUID.randomUUID().toString())
                .firstName(tokenPayload.getFirstName())
                .lastName(tokenPayload.getLastName())
                .avatar(tokenPayload.getAvatar())
                .enabled(true)
                .roleId(uid)
                .build();

        when(userStore.create(any())).thenReturn(newUser);

        final JwtUserDetails userDetails = new JwtUserDetails(newUser, List.of(new SimpleGrantedAuthority("CONTRIBUTOR")));


        final var actual = googleLoginService.loginGoogle(anyString());
        assertEquals(userDetails, actual);

        verify(googleTokenVerifierService).googleIdTokenVerifier(anyString());
        verify(userStore).findByUsername(tokenPayload.getEmail());
        verify(roleStore).findIdByName(anyString());
        verify(userStore).create(any());
    }

}