package com.phamhieu.bookapi.domain.profile;

import com.phamhieu.bookapi.domain.auth.AuthsProvider;
import com.phamhieu.bookapi.error.BadRequestException;
import com.phamhieu.bookapi.error.NotFoundException;
import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.phamhieu.bookapi.fakes.UserAuthenticationTokenFakes.buildContributor;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RequiredArgsConstructor
class ProfileServiceTest {

    @Mock
    private UserStore userStore;

    @Mock
    private AuthsProvider authsProvider;

    @InjectMocks
    private ProfileService profileService;

    @Spy
    BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void shouldFindProfile_OK() {
        when(authsProvider.getCurrentAuthentication()).thenReturn(buildContributor());
        final var userToken = authsProvider.getCurrentAuthentication();
        final var expected = buildUser();
        expected.setId(userToken.getUserId());
        when(userStore.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        final var actual = profileService.findProfile();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.getRoleId(), actual.getRoleId());
        verify(userStore).findById(expected.getId());
    }

    @Test
    void shouFindProfile_Throw() {
        when(authsProvider.getCurrentAuthentication()).thenReturn(buildContributor());
        final var userToken = authsProvider.getCurrentAuthentication();
        when(userStore.findById(userToken.getUserId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> profileService.findProfile());
        verify(userStore).findById(userToken.getUserId());
    }

    @Test
    void shouldUpdate_OK() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(6, 10));

        when(authsProvider.getCurrentAuthentication()).thenReturn(buildContributor());
        user.setId(authsProvider.getCurrentAuthentication().getUserId());
        when(userStore.findById((user.getId()))).thenReturn(Optional.of(user));
        when(userStore.update(user)).thenReturn(user);

        final var actual = profileService.update(user);

        assertEquals(user.getId().toString(), actual.getId().toString());
        assertEquals(user.getUsername(), actual.getUsername());
        assertEquals(user.getFirstName(), actual.getFirstName());
        assertEquals(user.getLastName(), actual.getLastName());
        assertEquals(user.getAvatar(), actual.getAvatar());
        assertEquals(user.getRoleId().toString(), actual.getRoleId().toString());
        assertEquals(user.isEnabled(), actual.isEnabled());

        verify(userStore).update(user);
    }

    @Test
    void shouldUpdateWithoutPassword_OK() {
        final var user = buildUser();
        user.setPassword(null);

        when(authsProvider.getCurrentAuthentication()).thenReturn(buildContributor());
        user.setId(authsProvider.getCurrentAuthentication().getUserId());
        when(userStore.findById((user.getId()))).thenReturn(Optional.of(user));
        when(userStore.update(user)).thenReturn(user);

        final var actual = profileService.update(user);

        assertEquals(user.getId().toString(), actual.getId().toString());
        assertEquals(user.getUsername(), actual.getUsername());
        assertEquals(user.getFirstName(), actual.getFirstName());
        assertEquals(user.getLastName(), actual.getLastName());
        assertEquals(user.getAvatar(), actual.getAvatar());
        assertEquals(user.getRoleId().toString(), actual.getRoleId().toString());
        assertEquals(user.isEnabled(), actual.isEnabled());

        verify(userStore).update(user);
    }

    @Test
    void shouldUpdate_ThrownPasswordLengthException() {
        final var user = buildUser();
        final var userUpdate = buildUser();
        userUpdate.setPassword(randomAlphabetic(1, 5));

        when(authsProvider.getCurrentAuthentication()).thenReturn(buildContributor());
        final var userToken = authsProvider.getCurrentAuthentication();
        user.setId(userToken.getUserId());
        when(userStore.findById((user.getId()))).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> profileService.update(userUpdate));
    }

    @Test
    void shouldUpdate_NotFound() {
        final var userUpdate = buildUser();
        userUpdate.setPassword(randomAlphabetic(6, 10));

        when(authsProvider.getCurrentAuthentication()).thenReturn(buildContributor());
        final var userToken = authsProvider.getCurrentAuthentication();
        userUpdate.setId(userToken.getUserId());
        when(userStore.findById(userUpdate.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> profileService.update(userUpdate));
        verify(userStore).findById(userUpdate.getId());
    }

}