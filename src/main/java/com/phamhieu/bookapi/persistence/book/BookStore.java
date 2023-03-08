package com.phamhieu.bookapi.persistence.book;

import com.phamhieu.bookapi.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.phamhieu.bookapi.persistence.book.BookEntityMapper.*;
import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@Repository
@RequiredArgsConstructor
public class BookStore {

    private final BookRepository bookRepository;

    public List<Book> findAllBook() {
        return toBooks(toList(bookRepository.findAll()));
    }

    public Optional<Book> findBookById(final UUID bookId) {
        return bookRepository.findById(bookId).map(BookEntityMapper::toBook);
    }

    public Book addBook(final Book book) {
        return toBook(bookRepository.save(toBookEntity(book)));
    }

    public List<Book> findByTitle(final String bookTitle) {
        return toBooks(bookRepository.findByTitle(bookTitle));
    }

    public List<Book> findByAuthor(final String author) {
        return toBooks(bookRepository.findByAuthor(author));
    }

    public Book updateBook(final Book book) {
        return toBook(bookRepository.save(toBookEntity(book)));
    }

    public void deleteBook(final UUID bookId) {
        bookRepository.deleteById(bookId);
    }
}
