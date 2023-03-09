package com.phamhieu.bookapi.domain.user;

import com.phamhieu.bookapi.domain.user.User;
import lombok.experimental.UtilityClass;

import static com.phamhieu.bookapi.domain.user.UserError.supplyNotEnoughInformation;
import static com.phamhieu.bookapi.domain.user.UserError.supplyPasswordLengthNotEnough;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UtilityClass
public class UserValidation {

    public void validateUserInfoCreate(final User user) {
        validateUsername(user.getUsername());
        validatePasswordCreate(user.getPassword());
        validatePasswordLength(user.getPassword());
    }

    public void validateUserInfoUpdate(final User user) {
        validateUsername(user.getUsername());
        validatePasswordLength(user.getPassword());
    }


    private static void validateUsername(final String username) {
        if (isBlank(username)) {
            throw supplyNotEnoughInformation("username").get();
        }
    }

    private static void validatePasswordCreate(final String password) {
        if (isBlank(password)) {
            throw supplyNotEnoughInformation("password").get();
        }
    }

    private static void validatePasswordLength(final String password) {
        if (isNotBlank(password)) {
            if (password.length() < 6) {
                throw supplyPasswordLengthNotEnough().get();
            }
        }
    }
}
