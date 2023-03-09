package com.phamhieu.bookapi.api.user;

import com.phamhieu.bookapi.domain.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class UserDTOMapper {
    public static UserResponseDTO toUserResponseDTO(final User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .roleId(user.getRoleId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.isEnabled())
                .avatar(user.getAvatar())
                .build();
    }

    public static List<UserResponseDTO> toUserDTOs(final List<User> users) {
        return emptyIfNull(users)
                .stream()
                .map(UserDTOMapper::toUserResponseDTO)
                .toList();
    }

    public static User toUser(final UserRequestDTO userRequestDTO) {
        return User.builder()
                .id(userRequestDTO.getId())
                .roleId(userRequestDTO.getRoleId())
                .username(userRequestDTO.getUsername())
                .password(userRequestDTO.getPassword())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .enabled(userRequestDTO.isEnabled())
                .avatar(userRequestDTO.getAvatar())
                .build();
    }
}
