package com.phamhieu.bookapi.domain.user;

import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

        verifyUsernameIfAvailable(user.getUsername());

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userStore.create(user);
    }

    public User update(final UUID userId, final User user) {
        validateUserInfoUpdate(user);
        final User tempUser = findById(userId);
        if (!equalsIgnoreCase(tempUser.getUsername(), user.getUsername())) {
            verifyUsernameIfAvailable(user.getUsername());
            tempUser.setUsername(user.getUsername());
        }

        if (isNotBlank(user.getPassword())) {
            tempUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

    private void verifyUsernameIfAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);
        if (userOptional.isPresent()) {
            throw supplyUserExist(username).get();
        }
    }
}
