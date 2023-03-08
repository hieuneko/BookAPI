package com.phamhieu.bookapi.domain.book;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class BookError {

    public static Supplier<NotFoundException> supplyBookNotFoundById(final UUID id) {
        return () -> new NotFoundException("Book has id:  %s couldn't be found", id);
    }

    public static Supplier<NotFoundException> supplyBookNotFoundByTitle(final String title) {
        return () -> new NotFoundException("Book has title name:  %s couldn't be found", title);
    }

    public static Supplier<NotFoundException> supplyBookNotFoundByAuthor(final String author) {
        return () -> new NotFoundException("Book has author name:  %s couldn't be found", author);
    }

    public static Supplier<BadRequestException> supplyNotEnoughInformation(final String inputColumn) {
        return () -> new BadRequestException("Information isn't not enough, " + inputColumn + "is null");
    }
}
