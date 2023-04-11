package com.phamhieu.bookapi.domain.book;

import com.phamhieu.bookapi.domain.auth.AuthsProvider;
import com.phamhieu.bookapi.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.phamhieu.bookapi.domain.auth.AuthError.supplyBookAccessDenied;
import static com.phamhieu.bookapi.domain.book.BookError.supplyIsbn13BookAlreadyExist;
import static com.phamhieu.bookapi.domain.book.BookError.supplyNotFound;
import static com.phamhieu.bookapi.domain.book.BookValidation.validateBook;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;

    private final AuthsProvider authsProvider;

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID bookId) {
        return bookStore.findById(bookId)
                .orElseThrow(supplyNotFound("id", String.valueOf(bookId)));
    }

    public List<Book> find(final String inputKeyword) {
        return bookStore.find(inputKeyword);
    }

    public Book create(final Book book) {
        validateBook(book);
        verifyIsbn13BookAvailable(book.getIsbn13());

        final double bookRating = book.getRating() == null ? 0.0 : book.getRating();

        book.setUserId(authsProvider.getCurrentAuthentication().getUserId());
        book.setCreatedAt(Instant.now());
        book.setRating(bookRating);
        return bookStore.create(book);
    }

    public Book update(final UUID bookId, final Book book) {
        validatePermissionWhenChangeBook(book);

        validateBook(book);
        final Book existingBook = findById(bookId);

        if (!existingBook.getIsbn13().equals(book.getIsbn13())) {
            verifyIsbn13BookAvailable(book.getIsbn13());
            existingBook.setIsbn13(book.getIsbn13());
        }
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setDescription(book.getDescription());
        existingBook.setUpdatedAt(Instant.now());
        existingBook.setImage(book.getImage());
        existingBook.setSubtitle(book.getSubtitle());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setPrice(book.getPrice());
        existingBook.setYear(book.getYear());
        existingBook.setRating(book.getRating());

        return bookStore.update(existingBook);
    }

    public void delete(final UUID bookId) {
        final Book book = findById(bookId);
        validatePermissionWhenChangeBook(book);

        bookStore.delete(book.getId());
    }

    private void validatePermissionWhenChangeBook(final Book book) {
        if (authsProvider.getCurrentRole().equals("CONTRIBUTOR") &&
                !authsProvider.getCurrentUserId().equals(book.getUserId())) {
            throw supplyBookAccessDenied().get();
        }
    }

    private void verifyIsbn13BookAvailable(final String isbn13) {
        bookStore.findBookByIsbn13(isbn13)
                .ifPresent(book -> {
                    throw supplyIsbn13BookAlreadyExist(book.getIsbn13()).get();
                });
    }
}
