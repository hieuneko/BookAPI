package com.phamhieu.bookapi.api.user;

import com.phamhieu.bookapi.error.BadRequestException;
import org.junit.jupiter.api.Test;

import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static com.phamhieu.bookapi.api.user.UserValidation.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

    @Test
    void shouldValidateUserInfoCreate_OK() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(6, 10));

        validateUserInfoCreate(user);
    }

    @Test
    void shouldValidateUserInfoCreate_UsernameOrPasswordEmpty() {
        final var user = buildUser();
        user.setUsername(null);

        assertThrows(BadRequestException.class, () -> validateUserInfoCreate(user));
    }

    @Test
    void shouldValidateUserInfoCreate_PasswordLengthNotEnough() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(1, 5));

        assertThrows(BadRequestException.class, () -> validateUserInfoUpdate(user));
    }

    @Test
    void shouldValidateUserInfoUpdate_OK() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(6, 10));

        validateUserInfoUpdate(user);
    }

    @Test
    void shouldValidateUserInfoCreate_UsernameEmpty() {
        final var user = buildUser();
        user.setUsername(null);

        assertThrows(BadRequestException.class, () -> validateUserInfoUpdate(user));
    }
}