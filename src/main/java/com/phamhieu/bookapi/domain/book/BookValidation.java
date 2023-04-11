package com.phamhieu.bookapi.domain.book;

import com.phamhieu.bookapi.error.BadRequestException;
import lombok.experimental.UtilityClass;

import static com.phamhieu.bookapi.domain.book.BookError.supplyIsbn13LengthIncorrect;
import static com.phamhieu.bookapi.domain.book.BookError.supplyNotEnoughInformation;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;
import static org.apache.commons.lang3.StringUtils.isBlank;

@UtilityClass
public class BookValidation {

    public void validateBook(final Book book) {
        validateTitle(book.getTitle());
        validateAuthor(book.getAuthor());
        validateSubtitle(book.getSubtitle());
        validatePublisher(book.getPublisher());
        validateIsbn13(book.getIsbn13());
        validatePrice(book.getPrice());
        validateYear(book.getYear());
        validateRating(book.getRating());
    }

    private void validateTitle(final String title) {
        if (isBlank(title)) {
            throw supplyNotEnoughInformation("title").get();
        }
    }

    private void validateAuthor(final String author) {
        if (isBlank(author)) {
            throw supplyNotEnoughInformation("author").get();
        }
    }

    private void validateSubtitle(final String subtitle) {
        if (isBlank(subtitle)) {
            throw supplyNotEnoughInformation("subtitle").get();
        }
    }

    private void validatePublisher(final String publisher) {
        if (isBlank(publisher)) {
            throw supplyNotEnoughInformation("publisher").get();
        }
    }

    private void validateIsbn13(final String isbn13) {
        if (isBlank(isbn13)) {
            throw supplyNotEnoughInformation("isbn13").get();
        }
        if (isbn13.length() != 13) {
            throw supplyIsbn13LengthIncorrect().get();
        }
    }

    private void validatePrice(final String price) {
        if (isBlank(price)) {
            throw supplyNotEnoughInformation("price").get();
        }
    }

    private void validateYear(final Integer year) {
        if (year == null) {
            throw supplyNotEnoughInformation("year").get();
        }
        if (year != getInstance().get(YEAR)) {
            throw new BadRequestException("Invalid year, check again");
        }
    }

    private void validateRating(final Double rating) {
        if (rating < 0 || rating > 5) {
            throw new BadRequestException("Rating in range from 0 to 5, check again");
        }
    }
}
