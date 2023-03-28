package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.error.UsernameNotFoundException;
import com.phamhieu.bookapi.persistence.role.RoleStore;
import com.phamhieu.bookapi.persistence.user.UserStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class JwtUserDetailsServiceTest {

    @Mock
    private UserStore userStore;

    @Mock
    private RoleStore roleStore;

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        jwtUserDetailsService = new JwtUserDetailsService(userStore, roleStore);
    }

    @Test
    public void loadUserByUsername_OK() {
        final var user = buildUser();
        final var role = randomAlphabetic(3, 10);

        when(userStore.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(roleStore.findRoleName(user.getRoleId())).thenReturn(role);

        final UserDetails actual = jwtUserDetailsService.loadUserByUsername(user.getUsername());

        assertEquals(user.getUsername(), actual.getUsername());
        verify(userStore).findByUsername(anyString());
        verify(roleStore).findRoleName(user.getRoleId());
    }

    @Test
    public void loadUserByUsername_shouldThrowUsernameNotFoundException() {
        final var username = randomAlphabetic(3, 10);
        when(userStore.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername(username));
        verify(userStore).findByUsername(anyString());
    }
}