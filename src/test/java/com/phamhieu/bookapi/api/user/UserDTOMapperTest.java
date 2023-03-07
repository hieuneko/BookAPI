package com.phamhieu.bookapi.api.user;

import org.junit.jupiter.api.Test;

import static com.phamhieu.bookapi.api.user.UserDTOMapper.*;
import static com.phamhieu.bookapi.fakes.UserFakes.*;
import static com.phamhieu.bookapi.persistence.user.UserEntityMapper.toUserEntity;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOMapperTest {

    @Test
    void shouldToUserDTOResponse_OK() {
        final var expected = buildUser();
        final var actual = toUserDTOResponse(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.getRoleId(), actual.getRoleId());
    }

    @Test
    void shouldToUserDTOs_OK() {
        final var expected = buildUsers();
        final var actual = toUserDTOs(expected);

        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldToUser_OK() {
        final var expected = buildUserDTORequest();
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
}