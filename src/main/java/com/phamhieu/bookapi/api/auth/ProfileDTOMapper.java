package com.phamhieu.bookapi.api.auth;

import com.phamhieu.bookapi.domain.auth.UserAuthenticationToken;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfileDTOMapper {

    public static ProfileDTO toProfileDTO(final UserAuthenticationToken userAuthenticationToken) {
        return ProfileDTO.builder()
                .userId(userAuthenticationToken.getUserId())
                .username(userAuthenticationToken.getUsername())
                .role(userAuthenticationToken.getRole())
                .build();
    }
}
