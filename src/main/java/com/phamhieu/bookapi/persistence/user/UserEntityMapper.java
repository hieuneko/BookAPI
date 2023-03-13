package com.phamhieu.bookapi.persistence.user;

import com.phamhieu.bookapi.domain.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class UserEntityMapper {

    public static User toUser(final UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .roleId(userEntity.getRoleId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .enabled(userEntity.isEnabled())
                .avatar(userEntity.getAvatar())
                .build();
    }

    public static List<User> toUsers(final List<UserEntity> userEntities) {
        return emptyIfNull(userEntities)
                .stream()
                .map(UserEntityMapper::toUser)
                .toList();
    }

    public static UserEntity toUserEntity(final User user) {
        return UserEntity.builder()
                .id(user.getId())
                .roleId(user.getRoleId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.isEnabled())
                .avatar(user.getAvatar())
                .build();
    }
}
