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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    @Value("${security.encode}")
    private String appSecurityCode;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User findUserById(final UUID userId) {
        return userStore.findUserById(userId)
                .orElseThrow(supplyUserNotFoundById(userId));
    }

    public List<User> findUserByName(String userName) {
        final List<User> users = userStore.findByNameContain(userName);
        if (users.size() == 0) {
            throw supplyUserNotFoundByName(userName).get();
        }
        return userStore.findByNameContain(userName);
    }

    public User addUser(final User user) throws NoSuchAlgorithmException {
        checkInformation(user);

        verifyUserIfAvailable(user);
        final User tempUser = User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(hashPassword(user.getPassword()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .enabled(user.isEnabled())
                .avatar(user.getAvatar())
                .roleId(user.getRoleId())
                .build();
        return userStore.addUser(tempUser);
    }

    public User updateUser(final User user, final UUID userId) throws NoSuchAlgorithmException {
        checkInformation(user);
        User tempUser = findUserById(userId);

        tempUser.setUsername(user.getUsername());
        tempUser.setPassword(hashPassword(user.getPassword()));
        tempUser.setFirstname(user.getFirstname());
        tempUser.setLastname(user.getLastname());
        tempUser.setEnabled(user.isEnabled());
        tempUser.setAvatar(user.getAvatar());
        return userStore.updateUser(tempUser);
    }

    public void deleteUser(final UUID userId) {
        userStore.deleteUser(userId);
    }

    private void checkInformation(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw supplyNotEnough().get();
        }
    }

    private void verifyUserIfAvailable(final User user) {
        final Optional<User> userOptional = userStore.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            throw supplyUserExist(user.getUsername()).get();
        }
    }

    String hashPassword(final String password) throws NoSuchAlgorithmException {
        final MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((password + appSecurityCode).getBytes());
        final byte[] digest = md5.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
