package com.phamhieu.bookapi.domain.book;

import com.phamhieu.bookapi.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.phamhieu.bookapi.domain.book.BookError.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;

    public List<Book> findAllBook() {
        return bookStore.findAllBook();
    }

    public Book findBookById(final UUID bookId) {
        return bookStore.findBookById(bookId)
                .orElseThrow(supplyBookNotFoundById(bookId));
    }

    public List<Book> findBookByTitle(String bookTitle) {
        final List<Book> books = bookStore.findByTitle(bookTitle);
        if (books.size() == 0) {
            throw supplyBookNotFoundByTitle(bookTitle).get();
        }
        return books;
    }

    public List<Book> findBookByAuthor(String author) {
        final List<Book> books = bookStore.findByAuthor(author);
        if (books.size() == 0) {
            throw supplyBookNotFoundByAuthor(author).get();
        }
        return books;
    }

    public Book addBook(final Book book){
        validateBookInformation(book);
        return bookStore.addBook(book);
    }

    public Book updateBook(final Book book, final UUID bookId){
        validateBookInformation(book);
        Book tempBook = findBookById(bookId);
        tempBook.setTitle(book.getTitle());
        tempBook.setAuthor(book.getAuthor());
        tempBook.setUpdatedAt(Instant.now());
        tempBook.setImage(book.getImage());
        tempBook.setUserId(book.getUserId());

        return bookStore.updateBook(tempBook);
    }

    public void deleteBook(final UUID bookId) {
        bookStore.deleteBook(bookId);
    }

    private void validateBookInformation(Book book) {
        if (book.getTitle() == null) {
            throw supplyNotEnoughInformation("title").get();
        }
        if (book.getAuthor() == null) {
            throw supplyNotEnoughInformation("author").get();
        }
    }
}
