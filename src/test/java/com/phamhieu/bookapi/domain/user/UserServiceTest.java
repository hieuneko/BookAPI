package com.phamhieu.bookapi.domain.user;

import com.phamhieu.bookapi.error.BadRequestException;
import com.phamhieu.bookapi.error.NotFoundException;
import com.phamhieu.bookapi.persistence.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Optional;

import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUsers;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserStore userStore;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildUsers();

        when(userStore.findAll())
                .thenReturn(expected);

        final var actual = userService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
        assertEquals(expected.get(0).getPassword(), actual.get(0).getPassword());
        assertEquals(expected.get(0).getFirstname(), actual.get(0).getFirstname());
        assertEquals(expected.get(0).getLastname(), actual.get(0).getLastname());
        assertEquals(expected.get(0).isEnabled(), actual.get(0).isEnabled());
        assertEquals(expected.get(0).getAvatar(), actual.get(0).getAvatar());
        assertEquals(expected.get(0).getRoleId(), actual.get(0).getRoleId());

        verify(userStore).findAll();
    }

    @Test
    void shouldFindUserById_OK() {
        final var expected = buildUser();
        when(userStore.findUserById(expected.getId()))
                .thenReturn(Optional.of(expected));

        assertEquals(expected, userService.findUserById(expected.getId()));
        verify(userStore).findUserById(expected.getId());
    }

    @Test
    void shouFindUserById_Throw() {
        final var id = randomUUID();
        when(userStore.findUserById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findUserById(id));
        verify(userStore).findUserById(id);
    }

    @Test
    void shouldFindUserByNameContain_OK() {
        final var user = buildUser();
        final var expected = buildUsers();

        when(userStore.findByNameContain(anyString())).thenReturn(expected);

        final var actual = userService.findUserByName(user.getUsername());

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
        assertEquals(expected.get(0).getFirstname(), actual.get(0).getFirstname());
        assertEquals(expected.get(0).getLastname(), actual.get(0).getLastname());
        assertEquals(expected.get(0).isEnabled(), actual.get(0).isEnabled());
        assertEquals(expected.get(0).getRoleId(), actual.get(0).getRoleId());
        assertEquals(expected.get(0).getAvatar(), actual.get(0).getAvatar());
    }

    @Test
    void shouldFindByUsernameOrFirstNameOrLastName_Thrown() {
        final var name = randomAlphabetic(3, 10);

        when(userStore.findByNameContain(name)).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> userService.findUserByName(name));
        verify(userStore).findByNameContain(name);
    }

    @Test
    void shouldAddUser_OK() throws NoSuchAlgorithmException {
        final var user = buildUser();

        when(userStore.addUser(any())).thenReturn(user);

        final var actual = userService.addUser(user);

        assertEquals(user.getId(), actual.getId());
        assertEquals(user.getUsername(), actual.getUsername());
        assertEquals(user.getPassword(), actual.getPassword());
        assertEquals(user.getFirstname(), actual.getFirstname());
        assertEquals(user.getLastname(), actual.getLastname());
        assertEquals(user.isEnabled(), actual.isEnabled());
        assertEquals(user.getAvatar(), actual.getAvatar());
        assertEquals(user.getRoleId(), actual.getRoleId());

        verify(userStore).addUser(any());
    }

    @Test
    void shouldAddUser_Throw() {
        final var expected = buildUser();
        expected.setPassword(null);

        assertThrows(BadRequestException.class, () -> userService.addUser(expected));
    }

    @Test
    void shouldAddUser_Exist() {
        final var expected = buildUser();

        when(userStore.findByUsername(expected.getUsername())).thenReturn(Optional.of(expected));

        assertThrows(BadRequestException.class, () -> userService.addUser(expected));
        verify(userStore).findByUsername(expected.getUsername());
    }

    @Test
    void shouldUpdateUser_OK() throws NoSuchAlgorithmException {
        final var user = buildUser();
        final var updatedUser = buildUser();
        updatedUser.setId(user.getId());
        updatedUser.setRoleId(user.getRoleId());

        when(userStore.findUserById((user.getId()))).thenReturn(Optional.of(user));
        when(userStore.updateUser(user)).thenReturn(user);

        final var expected = userService.updateUser(updatedUser, user.getId());

        assertEquals(expected.getId().toString(), updatedUser.getId().toString());
        assertEquals(expected.getUsername(), updatedUser.getUsername());
        assertEquals(expected.getFirstname(), updatedUser.getFirstname());
        assertEquals(expected.getLastname(), updatedUser.getLastname());
        assertEquals(expected.getAvatar(), updatedUser.getAvatar());
        assertEquals(expected.getRoleId().toString(), updatedUser.getRoleId().toString());
        assertEquals(expected.isEnabled(), updatedUser.isEnabled());

        verify(userStore).updateUser(user);
    }

    @Test
    void shouldUpdateUser_Thrown() {
        final var user = buildUser();
        final var updatedUser = buildUser();
        updatedUser.setPassword(null);

        assertThrows(BadRequestException.class, () -> userService.updateUser(updatedUser, user.getId()));
    }

    @Test
    void shouldUpdateUser_NotFound() {
        final var updatedUser = buildUser();
        final var id = randomUUID();

        when(userStore.findUserById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateUser(updatedUser, id));
        verify(userStore).findUserById(id);
    }

    @Test
    void shouldDeleteById_Ok() {
        final var user = buildUser();

        userService.deleteUser(user.getId());
        verify(userStore).deleteUser(user.getId());
    }

    @Test
    void shouldDeleteById_NotFound() {
        final var id = randomUUID();

        when(userStore.findUserById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findUserById(id));
        verify(userStore).findUserById(id);
    }

}