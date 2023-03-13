package com.phamhieu.bookapi.domain.user;

import com.phamhieu.bookapi.error.BadRequestException;
import com.phamhieu.bookapi.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class UserError {

    public static Supplier<NotFoundException> supplyUserNotFoundById(final UUID id) {
        return () -> new NotFoundException("User has id: %s couldn't be found", id);
    }

    public static Supplier<BadRequestException> supplyPasswordLengthNotEnough() {
        return () -> new BadRequestException("Password's length must be at least 6 characters");
    }

    public static Supplier<BadRequestException> supplyUserExist(final String username) {
        return () -> new BadRequestException("Username: %s already exist", username);
    }

    public static Supplier<BadRequestException> supplyNotEnoughInformation(final String field) {
        return () -> new BadRequestException("Information isn't not enough, " + field + " must be required");
    }
}
