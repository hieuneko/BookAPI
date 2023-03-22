package com.phamhieu.bookapi.domain.profile;

import com.phamhieu.bookapi.domain.user.User;
import lombok.experimental.UtilityClass;

import static com.phamhieu.bookapi.domain.user.UserError.supplyPasswordLengthNotEnough;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UtilityClass
public class ProfileValidation {

    public void validateProfileInfoUpdate(final User user) {
        validatePasswordLength(user.getPassword());
    }


    private static void validatePasswordLength(final String password) {
        if (isNotBlank(password) && password.length() < 6) {
            throw supplyPasswordLengthNotEnough().get();
        }
    }
}
