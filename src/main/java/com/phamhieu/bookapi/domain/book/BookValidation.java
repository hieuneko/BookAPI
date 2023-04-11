package com.phamhieu.bookapi.domain.book;

import lombok.experimental.UtilityClass;

import static com.phamhieu.bookapi.domain.book.BookError.supplyNotEnoughInformation;
import static org.apache.commons.lang3.StringUtils.isBlank;

@UtilityClass
public class BookValidation {

    public void validateBook(final Book book) {
        validateTitle(book.getTitle());
        validateAuthor(book.getAuthor());
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
    }

    private void validateSubtitle(final String subtitle) {
        if (isBlank(subtitle)) {
            throw supplyNotEnoughInformation("subtitle").get();
        }
    }

    private void validateSubtitle(final String subtitle) {
        if (isBlank(subtitle)) {
            throw supplyNotEnoughInformation("subtitle").get();
        }
    }

    private void validateSubtitle(final String subtitle) {
        if (isBlank(subtitle)) {
            throw supplyNotEnoughInformation("subtitle").get();
        }
    }

    private void validateSubtitle(final String subtitle) {
        if (isBlank(subtitle)) {
            throw supplyNotEnoughInformation("subtitle").get();
        }
    }

    private void validateSubtitle(final String subtitle) {
        if (isBlank(subtitle)) {
            throw supplyNotEnoughInformation("subtitle").get();
        }
    }

    private void validateSubtitle(final String subtitle) {
        if (isBlank(subtitle)) {
            throw supplyNotEnoughInformation("subtitle").get();
        }
    }
}
