package com.phamhieu.bookapi.persistence.user;

import org.junit.jupiter.api.Test;

import static com.phamhieu.bookapi.fakes.UserFakes.*;
import static com.phamhieu.bookapi.persistence.user.UserEntityMapper.*;
import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    @Test
    void shouldToUser_OK() {
        final var expected = buildUserEntity();
        final var actual = toUser(expected);

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
    void shouldToUsers_OK() {
        final var expected = buildUserEntities();
        final var actual = toUsers(expected);

        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldToUserEntity_OK() {
        final var expected = buildUser();
        final var actual = toUserEntity(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.getRoleId(), actual.getRoleId());
    }
}