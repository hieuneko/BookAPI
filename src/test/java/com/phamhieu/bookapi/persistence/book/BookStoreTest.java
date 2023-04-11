package com.phamhieu.bookapi.persistence.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.phamhieu.bookapi.fakes.BookFakes.buildBookEntities;
import static com.phamhieu.bookapi.fakes.BookFakes.buildBookEntity;
import static com.phamhieu.bookapi.persistence.book.BookEntityMapper.toBook;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookStoreTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookStore bookStore;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildBookEntities();

        when(bookRepository.findAll())
                .thenReturn(expected);

        assertEquals(expected.size(), bookStore.findAll().size());

        verify(bookRepository).findAll();
    }

    @Test
    void shouldFindById_OK() {
        final var book = buildBookEntity();
        final var bookOpt = Optional.of(book);

        when(bookRepository.findById(book.getId())).thenReturn(bookOpt);

        final var actual = bookStore.findById(book.getId()).get();
        final var expected = bookOpt.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getUserId(), actual.getUserId());

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void shouldFindById_Empty() {
        final var id = randomUUID();
        final Optional<BookEntity> bookOpt = Optional.empty();

        when(bookRepository.findById(id))
                .thenReturn(bookOpt);

        final var actual = bookStore.findById(id);

        assertFalse(actual.isPresent());
        verify(bookRepository).findById(id);
    }

    @Test
    void shouldFind_OK() {
        final var input = randomAlphabetic(3, 10);
        final var expected = buildBookEntities();

        when(bookRepository.findByKeyword(input))
                .thenReturn(expected);

        final var actual = bookStore.find(input);

        assertEquals(actual.size(), expected.size());

        verify(bookRepository).findByKeyword(input);
    }

    @Test
    void shouldFind_Empty() {
        final var input = randomAlphabetic(3, 10);
        when(bookRepository.findByKeyword(input))
                .thenReturn(Collections.emptyList());
        final var actual = bookStore.find(input);

        assertTrue(actual.isEmpty());
        verify(bookRepository).findByKeyword(input);
    }

    @Test
    void shouldFindBookByIsbn13_OK() {
        final var book = buildBookEntity();

        when(bookRepository.findByIsbn13(book.getIsbn13())).thenReturn(Optional.of(book));

        final var actual = bookStore.findBookByIsbn13(book.getIsbn13()).get();

        assertNotNull(actual);
        assertEquals(book.getId(), actual.getId());
        assertEquals(book.getTitle(), actual.getTitle());
        assertEquals(book.getAuthor(), actual.getAuthor());
        assertEquals(book.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(book.getCreatedAt(), actual.getCreatedAt());
        assertEquals(book.getDescription(), actual.getDescription());
        assertEquals(book.getImage(), actual.getImage());
        assertEquals(book.getSubtitle(), actual.getSubtitle());
        assertEquals(book.getPublisher(), actual.getPublisher());
        assertEquals(book.getIsbn13(), actual.getIsbn13());
        assertEquals(book.getPrice(), actual.getPrice());
        assertEquals(book.getYear(), actual.getYear());
        assertEquals(book.getRating(), actual.getRating());
        assertEquals(book.getUserId(), actual.getUserId());

        verify(bookRepository).findByIsbn13(book.getIsbn13());
    }

    @Test
    void shouldFindBookByIsbn13_Empty() {
        final var isbn13 = randomNumeric(13);

        when(bookRepository.findByIsbn13(isbn13)).thenReturn(Optional.empty());

        assertFalse(bookStore.findBookByIsbn13(isbn13).isPresent());
        verify(bookRepository).findByIsbn13(isbn13);
    }

    @Test
    void shouldCreate_OK() {
        final var expected = buildBookEntity();
        when(bookRepository.save(any(BookEntity.class))).thenReturn(expected);
        final var actual = bookStore.create(toBook(expected));

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getUserId(), actual.getUserId());
    }

    @Test
    void shouldUpdate_OK() {
        final var expected = buildBookEntity();
        when(bookRepository.save(any(BookEntity.class))).thenReturn(expected);
        final var actual = bookStore.update(toBook(expected));

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getUserId(), actual.getUserId());
    }

    @Test
    void shouldDelete_OK() {
        final var bookId = randomUUID();

        assertDoesNotThrow(() -> bookStore.delete(bookId));
    }
}