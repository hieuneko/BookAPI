package com.phamhieu.bookapi.persistence.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.phamhieu.bookapi.fakes.UserFakes.buildUserEntities;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUserEntity;
import static com.phamhieu.bookapi.persistence.user.UserEntityMapper.toUser;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserStoreTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserStore userStore;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildUserEntities();

        when(userRepository.findAll()).thenReturn(expected);

        assertEquals(expected.size(), userStore.findAll().size());

        verify(userRepository).findAll();
    }

    @Test
    void shouldFindById_OK() {
        final var user = buildUserEntity();
        final var userOpt = Optional.of(user);
        when(userRepository.findById(user.getId()))
                .thenReturn(userOpt);

        assertEquals(userOpt, userRepository.findById(user.getId()));
        verify(userRepository).findById(user.getId());
    }

    @Test
    void shouldFindById_Empty() {
        final var id = randomUUID();
        final Optional<UserEntity> userOpt = Optional.empty();
        when(userRepository.findById(id))
                .thenReturn(userOpt);

        assertFalse(userRepository.findById(id).isPresent());
        verify(userRepository).findById(id);
    }

    @Test
    void shouldFindByUsername_OK() {
        final var user = buildUserEntity();
        final var userOpt = Optional.of(user);
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(userOpt);

        assertEquals(userOpt, userRepository.findByUsername(user.getUsername()));
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void shouldFindByUsername_Empty() {
        final var name = randomAlphabetic(3, 10);
        final Optional<UserEntity> userOpt = Optional.empty();
        when(userRepository.findByUsername(name))
                .thenReturn(userOpt);

        assertFalse(userRepository.findByUsername(name).isPresent());
        verify(userRepository).findByUsername(name);
    }

    @Test
    void shouldFindByNameContain_OK() {
        final var user = buildUserEntity();
        final var expected = buildUserEntities();
        when(userRepository.findByUsernameOrFirstnameOrLastname(anyString(), anyString(), anyString()))
                .thenReturn(expected);
        final var actual = userStore.findByNameContain(user.getUsername());

        assertEquals(actual.size(), expected.size());
        verify(userRepository).findByUsernameOrFirstnameOrLastname(user.getUsername(), user.getUsername(), user.getUsername());
    }

    @Test
    void shouldFindByNameContain_Empty() {
        final var name = randomAlphabetic(3, 10);
        when(userRepository.findByUsernameOrFirstnameOrLastname(name, name, name))
                .thenReturn(Collections.emptyList());
        final var actual = userStore.findByNameContain(name);

        assertTrue(actual.isEmpty());
        verify(userRepository).findByUsernameOrFirstnameOrLastname(name, name, name);
    }

    @Test
    void shouldAddUser_OK() {
        final var expected = buildUserEntity();
        when(userRepository.save(any(UserEntity.class))).thenReturn(expected);
        final var actual = userStore.addUser(toUser(expected));

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.getRoleId(), actual.getRoleId());
    }

    @Test
    void shouldUpdateUser_OK() {
        final var expected = buildUserEntity();
        when(userRepository.save(any(UserEntity.class))).thenReturn(expected);
        final var actual = userStore.updateUser(toUser(expected));

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.getRoleId(), actual.getRoleId());
    }

    @Test
    void shouldDeleteUser_OK() {
        final var user = buildUserEntity();
        userStore.deleteUser(user.getId());

        verify(userRepository).deleteById(user.getId());
    }
}