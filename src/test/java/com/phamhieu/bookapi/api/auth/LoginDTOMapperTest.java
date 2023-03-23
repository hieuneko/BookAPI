package com.phamhieu.bookapi.api.auth;

import org.junit.jupiter.api.Test;

import static com.phamhieu.bookapi.api.auth.LoginDTOMapper.toAuthentication;
import static com.phamhieu.bookapi.fakes.LoginFakes.buildLogin;
import static org.junit.jupiter.api.Assertions.*;

class LoginDTOMapperTest {

    @Test
    void shouldToAuthentication_OK() {
        final var login = buildLogin();
        final var authentication = toAuthentication(login);

        assertEquals(login.getUsername(), authentication.getPrincipal());
        assertEquals(login.getPassword(), authentication.getCredentials());
    }
}