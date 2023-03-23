package com.phamhieu.bookapi.fakes;

import com.phamhieu.bookapi.api.profile.ProfileRequestDTO;
import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class ProfileFakes {

    public static ProfileRequestDTO buildProfileDTORequest() {
        return ProfileRequestDTO.builder()
                .username(randomAlphabetic(3, 10))
                .password(randomAlphabetic(3, 10))
                .firstName(randomAlphabetic(3, 10))
                .lastName(randomAlphabetic(3, 10))
                .avatar(randomAlphabetic(3, 10))
                .build();
    }
}
