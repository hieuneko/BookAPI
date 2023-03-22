package com.phamhieu.bookapi.domain.profile;

import com.phamhieu.bookapi.domain.auth.AuthsProvider;
import com.phamhieu.bookapi.domain.user.User;
import com.phamhieu.bookapi.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.phamhieu.bookapi.domain.profile.ProfileValidation.*;
import static com.phamhieu.bookapi.domain.user.UserError.supplyUserNotFoundById;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserStore userStore;

    private final AuthsProvider authsProvider;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findProfile() {
        final UUID userId = getUserIdFromToken();
        return userStore.findById(userId).
                orElseThrow(supplyUserNotFoundById(userId));
    }

    public User update(final User user) {
        validateProfileInfoUpdate(user);

        final User tempUser = findProfile();
        if (isNotBlank(tempUser.getPassword())) {
            tempUser.setPassword(bCryptPasswordEncoder.encode(tempUser.getPassword()));
        }
        tempUser.setFirstName(user.getFirstName());
        tempUser.setLastName(user.getLastName());
        tempUser.setAvatar(user.getAvatar());
        return userStore.update(tempUser);
    }

    private UUID getUserIdFromToken() {
        return authsProvider.getCurrentAuthentication().getUserId();
    }
}
