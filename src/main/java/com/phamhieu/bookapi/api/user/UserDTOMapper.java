package com.phamhieu.bookapi.api.user;

import com.phamhieu.bookapi.domain.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class UserDTOMapper {
    public static UserDTOResponse toUserDTOResponse(final User user) {
        return UserDTOResponse.builder()
                .id(user.getId())
                .roleId(user.getRoleId())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .enabled(user.isEnabled())
                .avatar(user.getAvatar())
                .build();
    }

    public static List<UserDTOResponse> toUserDTOs(final List<User> users) {
        return emptyIfNull(users)
                .stream()
                .map(UserDTOMapper::toUserDTOResponse)
                .toList();
    }

    public static User toUser(final UserDTORequest userDTORequest) {
        return User.builder()
                .id(userDTORequest.getId())
                .roleId(userDTORequest.getRoleId())
                .username(userDTORequest.getUsername())
                .password(userDTORequest.getPassword())
                .firstname(userDTORequest.getFirstname())
                .lastname(userDTORequest.getLastname())
                .enabled(userDTORequest.isEnabled())
                .avatar(userDTORequest.getAvatar())
                .build();
    }
}
