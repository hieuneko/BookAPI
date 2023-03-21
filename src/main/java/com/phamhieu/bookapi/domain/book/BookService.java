package com.phamhieu.bookapi.domain.book;

import com.phamhieu.bookapi.domain.auth.AuthsProvider;
import com.phamhieu.bookapi.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.phamhieu.bookapi.domain.auth.AuthError.supplyAuthorizationAccessNeeded;
import static com.phamhieu.bookapi.domain.book.BookValidation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

import static com.phamhieu.bookapi.domain.book.BookError.*;

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
                .orElseThrow(supplyBookNotFoundById(bookId));
    }

    public List<Book> find(final String inputKeyword) {
        return bookStore.find(inputKeyword);
    }

    public Book create(final Book book) {
        validateBook(book);

        book.setUserId(authsProvider.getCurrentAuthentication().getUserId());
        book.setCreatedAt(Instant.now());
        return bookStore.create(book);
    }

    public Book update(final UUID bookId, final Book book) {
        validateAuthPermission(book.getUserId());

        validateBook(book);
        final Book existingBook = findById(bookId);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setDescription(book.getDescription());
        existingBook.setUpdatedAt(Instant.now());
        existingBook.setImage(book.getImage());
        existingBook.setUserId(book.getUserId());

        return bookStore.update(existingBook);
    }

    public void delete(final UUID bookId) {
        final Book book = findById(bookId);
        validateAuthPermission(book.getUserId());

        bookStore.delete(book.getId());
    }

    private void validateAuthPermission(final UUID userId) {
        if (StringUtils.equals(authsProvider.getCurrentUserRole(), "ROLE_ADMIN")) {
            return;
        }
        if (!StringUtils.equals(authsProvider.getCurrentUserId(), userId.toString())) {
            throw supplyAuthorizationAccessNeeded().get();
        }
    }
}
