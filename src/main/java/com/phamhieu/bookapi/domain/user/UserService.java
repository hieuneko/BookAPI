package com.phamhieu.bookapi.domain.user;

import com.phamhieu.bookapi.domain.auth.AuthsProvider;
import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.phamhieu.bookapi.domain.user.UserError.*;
import static com.phamhieu.bookapi.domain.user.UserValidation.*;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    private final AuthsProvider authsProvider;

    private final PasswordEncoder passwordEncoder;

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

    public User findProfile() {
        return User.builder()
                .id(authsProvider.getCurrentUserId())
                .username(authsProvider.getCurrentUsername())
                .firstName(authsProvider.getCurrentFirstName())
                .lastName(authsProvider.getCurrentLastName())
                .avatar(authsProvider.getCurrentAvatar())
                .build();
    }

    public User create(final User user) throws NoSuchAlgorithmException {
        validateUserInfoCreate(user);

        verifyUsernameIfAvailable(user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userStore.create(user);
    }

    public User update(final UUID userId, final User user) {
        validateUserInfoUpdate(user);
        final User existUser = findById(userId);
        if (!equalsIgnoreCase(existUser.getUsername(), user.getUsername())) {
            verifyUsernameIfAvailable(user.getUsername());
            existUser.setUsername(user.getUsername());
        }

        if (isNotBlank(user.getPassword())) {
            existUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        existUser.setFirstName(user.getFirstName());
        existUser.setLastName(user.getLastName());
        existUser.setEnabled(user.isEnabled());
        existUser.setAvatar(user.getAvatar());
        existUser.setRoleId(user.getRoleId());
        return userStore.update(existUser);
    }

    public User updateProfile(final User user) {
        return update(authsProvider.getCurrentUserId(), user);
    }

    public void delete(final UUID userId) {
        userStore.delete(userId);
    }

    private void verifyUsernameIfAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);
        if (userOptional.isPresent()) {
            throw supplyUserExist(username).get();
        }
    }
}
