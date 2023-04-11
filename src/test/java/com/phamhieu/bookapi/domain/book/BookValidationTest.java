package com.phamhieu.bookapi.domain.book;

import com.phamhieu.bookapi.error.BadRequestException;
import org.junit.jupiter.api.Test;

import static com.phamhieu.bookapi.domain.book.BookValidation.validateBook;
import static com.phamhieu.bookapi.fakes.BookFakes.buildBook;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookValidationTest {

    @Test
    void shouldValidateBookInformation() {
        final var book = buildBook();
        book.setYear(2023);
        book.setRating(4.0);

        assertDoesNotThrow(() -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_TitleIsNull() {
        final var book = buildBook();
        book.setTitle(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_AuthorIsNull() {
        final var book = buildBook();
        book.setAuthor(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_SubtitleIsNull() {
        final var book = buildBook();
        book.setSubtitle(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_PublisherIsNull() {
        final var book = buildBook();
        book.setPublisher(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_Isbn13IsNull() {
        final var book = buildBook();
        book.setIsbn13(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_Isbn13LengthIncorrect() {
        final var book = buildBook();
        book.setIsbn13(randomNumeric(15));

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_PriceIsNull() {
        final var book = buildBook();
        book.setPrice(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_YearIsNull() {
        final var book = buildBook();
        book.setYear(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_InvalidYear() {
        final var book = buildBook();
        book.setYear(2024);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_InvalidRatingLow() {
        final var book = buildBook();
        book.setRating(-0.1);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void shouldValidateBookInformation_InvalidRatingHigh() {
        final var book = buildBook();
        book.setRating(5.1);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }
}