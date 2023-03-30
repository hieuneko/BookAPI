package com.phamhieu.bookapi.domain.auth;


import com.google.firebase.auth.FirebaseAuthException;
import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.error.BadRequestException;
import com.phamhieu.bookapi.persistence.role.RoleStore;
import com.phamhieu.bookapi.persistence.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.phamhieu.bookapi.fakes.FirebaseTokenPayloadFakes.buildToken;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirebaseLoginServiceTest {

    @Mock
    private UserStore userStore;

    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private RoleStore roleStore;

    @Mock
    private FirebaseTokenVerifierService firebaseTokenVerifierService;

    @InjectMocks
    private FirebaseLoginService firebaseLoginService;


    @Test
    void shouldLoginGoogle_OK() throws FirebaseAuthException {
        ReflectionTestUtils.setField(firebaseLoginService, "firebaseProjectId", "book-api-2b55e");
        final var user = buildUser();
        final FirebaseTokenPayload firebaseToken = buildToken();
        final JwtUserDetails userDetails = new JwtUserDetails(user, List.of(new SimpleGrantedAuthority("CONTRIBUTOR")));

        user.setUsername(firebaseToken.getEmail());

        when(firebaseTokenVerifierService.verifyToken(anyString())).thenReturn(firebaseToken);
        when(userStore.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(jwtUserDetailsService.loadUserByUsername(user.getUsername())).thenReturn(userDetails);

        final var actual = firebaseLoginService.loginGoogle(anyString());

        assertEquals(userDetails, actual);

        verify(firebaseTokenVerifierService).verifyToken(anyString());
        verify(userStore).findByUsername(user.getUsername());
        verify(jwtUserDetailsService).loadUserByUsername(user.getUsername());
    }

    @Test
    void shouldLoginGoogle_UserNotFound() throws FirebaseAuthException {
        ReflectionTestUtils.setField(firebaseLoginService, "firebaseProjectId", "book-api-2b55e");
        final FirebaseTokenPayload firebaseToken = buildToken();

        when(firebaseTokenVerifierService.verifyToken(anyString())).thenReturn(firebaseToken);
        when(userStore.findByUsername(firebaseToken.getEmail())).thenReturn(Optional.empty());

        final var uid = UUID.randomUUID();

        when(roleStore.findIdByName(anyString())).thenReturn(uid);

        final User newUser = User.builder()
                .username(firebaseToken.getEmail())
                .password(UUID.randomUUID().toString())
                .enabled(true)
                .roleId(uid)
                .build();

        when(userStore.create(any())).thenReturn(newUser);

        final JwtUserDetails userDetails = new JwtUserDetails(newUser, List.of(new SimpleGrantedAuthority("CONTRIBUTOR")));

        when(jwtUserDetailsService.loadUserByUsername(newUser.getUsername())).thenReturn(userDetails);

        final var actual = firebaseLoginService.loginGoogle(anyString());
        assertEquals(userDetails, actual);

        verify(firebaseTokenVerifierService).verifyToken(anyString());
        verify(userStore).findByUsername(firebaseToken.getEmail());
        verify(roleStore).findIdByName(anyString());
        verify(userStore).create(any());
        verify(jwtUserDetailsService).loadUserByUsername(newUser.getUsername());
    }

    @Test
    void shouldLoginGoogle_ThrowProjectIdIncorrect() throws FirebaseAuthException {
        ReflectionTestUtils.setField(firebaseLoginService, "firebaseProjectId", "book-api-2b55e");
        final FirebaseTokenPayload firebaseToken = buildToken();
        firebaseToken.setProjectId("project-mock");

        when(firebaseTokenVerifierService.verifyToken(anyString())).thenReturn(firebaseToken);

        assertThrows(BadRequestException.class, () -> firebaseLoginService.loginGoogle(anyString()));

        verify(firebaseTokenVerifierService).verifyToken(anyString());
    }
}