package com.phamhieu.bookapi.domain.profile;

import com.phamhieu.bookapi.error.BadRequestException;
import org.junit.jupiter.api.Test;

import static com.phamhieu.bookapi.domain.profile.ProfileValidation.*;
import static com.phamhieu.bookapi.fakes.UserFakes.buildUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;

class ProfileValidationTest {

    @Test
    void shouldValidateProfileInfoUpdate_OK() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(6, 10));

        assertDoesNotThrow(() -> validateProfileInfoUpdate(user));
    }

    @Test
    void shouldValidateUserInfoCreate_PasswordLengthNotEnough() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(1, 5));

        assertThrows(BadRequestException.class, () -> validateProfileInfoUpdate(user));
    }
}