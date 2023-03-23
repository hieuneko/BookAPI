package com.phamhieu.bookapi.api.profile;

import org.junit.jupiter.api.Test;

import static com.phamhieu.bookapi.api.profile.ProfileDTOMapper.*;
import static com.phamhieu.bookapi.fakes.ProfileFakes.buildProfileDTORequest;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static org.junit.jupiter.api.Assertions.*;

class ProfileDTOMapperTest {

    @Test
    void shouldToProfileResponseDTO_OK() {
        final var expected = buildUser();
        final var actual = toProfileResponseDTO(expected);

        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAvatar(), actual.getAvatar());
    }

    @Test
    void shouldToProfile_OK() {
        final var expected = buildProfileDTORequest();
        final var actual = toUser(expected);

        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAvatar(), actual.getAvatar());
    }
}