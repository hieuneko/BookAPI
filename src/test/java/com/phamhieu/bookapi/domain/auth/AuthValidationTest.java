package com.phamhieu.bookapi.domain.auth;

import com.phamhieu.bookapi.api.WithMockAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;

import static com.phamhieu.bookapi.domain.auth.AuthValidation.*;

class AuthValidationTest {

    @Test
    @WithMockAdmin
    void shouldValidateAuth_OK() {
        final UUID id = randomUUID();

        assertDoesNotThrow(() -> validateAuth(authsProvider, id));
    }

//    @Test
//    void shouldValidateBookInformation_TitleOrAuthorIsNull() {
//        authsProvider.getCurrentAuthentication().
//
//        assertThrows(BadRequestException.class, () -> validateBook(book));
//    }
}