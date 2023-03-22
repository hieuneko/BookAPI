package com.phamhieu.bookapi.api.profile;

import com.phamhieu.bookapi.domain.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfileDTOMapper {
    public static ProfileResponseDTO toProfileResponseDTO(final User user) {
        return ProfileResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .build();
    }

    public static User toUser(final ProfileRequestDTO profileRequestDTO) {
        return User.builder()
                .password(profileRequestDTO.getPassword())
                .firstName(profileRequestDTO.getFirstName())
                .lastName(profileRequestDTO.getLastName())
                .avatar(profileRequestDTO.getAvatar())
                .build();
    }
}
