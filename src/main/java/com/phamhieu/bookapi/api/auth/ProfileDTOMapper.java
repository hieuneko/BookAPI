package com.phamhieu.bookapi.api.auth;

import com.phamhieu.bookapi.domain.auth.UserAuthenticationToken;
import com.phamhieu.bookapi.domain.user.User;
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

    public static ProfileDTO toProfileDTO(final User user) {
        return ProfileDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    public static User toUser(final ProfileDTO profileDTO) {
        return User.builder()
                .id(profileDTO.getUserId())
                .username(profileDTO.getUsername())
                .build();
    }
}
