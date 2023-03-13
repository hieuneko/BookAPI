package com.phamhieu.bookapi.domain.book;

import com.phamhieu.bookapi.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.phamhieu.bookapi.domain.book.BookValidation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.phamhieu.bookapi.domain.book.BookError.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID bookId) {
        return bookStore.findById(bookId)
                .orElseThrow(supplyBookNotFoundById(bookId));
    }

    public List<Book> findByTitle(String bookTitle) {
        final List<Book> books = bookStore.findByTitle(bookTitle);
        return books;
    }

    public List<Book> findByAuthor(String author) {
        final List<Book> books = bookStore.findByAuthor(author);
        return books;
    }

    public Book create(final Book book) {
        validateBookInformation(book);
        return bookStore.create(book);
    }

    public Book update(final UUID bookId, final Book book) {
        validateBookInformation(book);
        Book tempBook = findById(bookId);
        tempBook.setTitle(book.getTitle());
        tempBook.setAuthor(book.getAuthor());
        tempBook.setDescription(book.getDescription());
        tempBook.setUpdatedAt(Instant.now());
        tempBook.setImage(book.getImage());
        tempBook.setUserId(book.getUserId());

        return bookStore.update(tempBook);
    }

    public void delete(final UUID bookId) {
        bookStore.delete(bookId);
    }
}
