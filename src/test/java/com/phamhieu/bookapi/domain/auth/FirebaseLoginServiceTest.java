package com.phamhieu.bookapi.domain.auth;


import com.google.firebase.auth.FirebaseAuthException;
import com.phamhieu.bookapi.error.BadRequestException;
import com.phamhieu.bookapi.error.NotFoundException;
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

import static com.phamhieu.bookapi.fakes.MyFirebaseTokenFakes.buildToken;
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
    private FirebaseTokenVerifierService firebaseTokenVerifierService;

    @InjectMocks
    private FirebaseLoginService firebaseLoginService;


    @Test
    void shouldLoginGoogle_OK() throws FirebaseAuthException {
        ReflectionTestUtils.setField(firebaseLoginService, "firebaseProjectId", "book-api-2b55e");
        final var user = buildUser();
        final MyFirebaseToken firebaseToken = buildToken();
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
        final MyFirebaseToken firebaseToken = buildToken();

        when(firebaseTokenVerifierService.verifyToken(anyString())).thenReturn(firebaseToken);
        when(userStore.findByUsername(firebaseToken.getEmail())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> firebaseLoginService.loginGoogle(anyString()));

        verify(firebaseTokenVerifierService).verifyToken(anyString());
        verify(userStore).findByUsername(firebaseToken.getEmail());
    }

    @Test
    void shouldLoginGoogle_ThrowProjectIdIncorrect() throws FirebaseAuthException {
        ReflectionTestUtils.setField(firebaseLoginService, "firebaseProjectId", "book-api-2b55e");
        final MyFirebaseToken firebaseToken = buildToken();
        firebaseToken.setProjectId("project-mock");

        when(firebaseTokenVerifierService.verifyToken(anyString())).thenReturn(firebaseToken);

        assertThrows(BadRequestException.class, () -> firebaseLoginService.loginGoogle(anyString()));

        verify(firebaseTokenVerifierService).verifyToken(anyString());
    }
}