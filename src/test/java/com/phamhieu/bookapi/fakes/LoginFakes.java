package com.phamhieu.bookapi.fakes;

import com.phamhieu.bookapi.api.auth.LoginDTO;
import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class LoginFakes {

    public static LoginDTO buildLogin() {
        return LoginDTO.builder()
                .username(randomAlphabetic(3, 10))
                .password(randomAlphabetic(3, 10))
                .build();
    }
}
