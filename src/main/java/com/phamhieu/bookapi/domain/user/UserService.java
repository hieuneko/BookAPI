package com.phamhieu.bookapi.domain.user;

import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.phamhieu.bookapi.domain.user.UserError.*;
import static com.phamhieu.bookapi.domain.user.UserValidation.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    @Value("${security.encode}")
    private String appSecurityCode;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User findById(final UUID userId) {
        return userStore.findById(userId)
                .orElseThrow(supplyUserNotFoundById(userId));
    }

    public List<User> find(final String userName) {
        return userStore.find(userName);
    }

    public User create(final User user) throws NoSuchAlgorithmException {
        validateUserInfoCreate(user);
        verifyUserIfAvailable(user.getUsername());

        final User tempUser = User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(hashPassword(user.getPassword()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.isEnabled())
                .avatar(user.getAvatar())
                .roleId(user.getRoleId())
                .build();
        return userStore.addUser(tempUser);
    }

    public User update(final UUID userId, final User user) throws NoSuchAlgorithmException {
        validateUserInfoUpdate(user);
        final User tempUser = findById(userId);
        verifyUserIfAvailableUpdate(tempUser, user.getUsername());

        tempUser.setUsername(user.getUsername());
        if (isNotBlank(user.getPassword())) {
            tempUser.setPassword(hashPassword(user.getPassword()));
        }
        tempUser.setFirstName(user.getFirstName());
        tempUser.setLastName(user.getLastName());
        tempUser.setEnabled(user.isEnabled());
        tempUser.setAvatar(user.getAvatar());
        tempUser.setRoleId(user.getRoleId());
        return userStore.update(tempUser);
    }

    public void delete(final UUID userId) {
        userStore.delete(userId);
    }

    private void verifyUserIfAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);
        if (userOptional.isPresent()) {
            throw supplyUserExist(username).get();
        }
    }

    private void verifyUserIfAvailableUpdate(final User user, final String username) {
        if (!username.equals(user.getUsername())) {
            verifyUserIfAvailable(username);
        }
    }

    private String hashPassword(final String password) throws NoSuchAlgorithmException {
        final MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((password + appSecurityCode).getBytes());
        final byte[] digest = md5.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
