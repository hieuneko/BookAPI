package com.phamhieu.bookapi.domain.book;

import com.phamhieu.bookapi.error.BadRequestException;
import com.phamhieu.bookapi.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class BookError {

    public static Supplier<NotFoundException> supplyNotFound(final String field, final String value) {
        return () -> new NotFoundException("Book has %s with value: %s couldn't be found", field, value);
    }

    public static Supplier<BadRequestException> supplyNotEnoughInformation(final String field) {
        return () -> new BadRequestException("Information isn't not enough, %s must be required", field);
    }

    public static Supplier<BadRequestException> supplyIsbn13LengthIncorrect() {
        return () -> new BadRequestException("Isbn13 is contain 13 characters, check length again");
    }

    public static Supplier<BadRequestException> supplyIsbn13BookAlreadyExist(final String isbn13) {
        return () -> new BadRequestException("Book has isbn13 code: %s already exist", isbn13);
    }
}
