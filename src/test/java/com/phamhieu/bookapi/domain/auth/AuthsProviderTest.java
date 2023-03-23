package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.error.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.phamhieu.bookapi.fakes.UserAuthenticationTokenFakes.buildAdmin;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthsProviderTest {

    @InjectMocks
    private AuthsProvider authsProvider;

    @Test
    void shouldGetCurrentAuthentication_OK() {
        final var expected = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(expected);

        final var actual = authsProvider.getCurrentAuthentication();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetCurrentAuthentication_Throw() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentAuthentication());
    }

    @Test
    void shouldGetCurrentUserId_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentUserId();

        assertEquals(user.getUserId(), actual);
    }

    @Test
    void shouldGetCurrentUserId_Throw() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentUserId());
    }

    @Test
    void shouldGetCurrentUsername_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentUsername();

        assertEquals(user.getUsername(), actual);
    }

    @Test
    void shouldGetCurrentUsername_Throw() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentUsername());
    }

    @Test
    void shouldGetCurrentRole_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentRole();

        assertEquals(user.getRole(), actual);
    }

    @Test
    void shouldGetCurrentRole_Throw() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentRole());
    }
}