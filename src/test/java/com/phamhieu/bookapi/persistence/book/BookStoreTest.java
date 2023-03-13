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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    void shouldFindByTitle_OK() {
        final var title = randomAlphabetic(3, 10);
        final var expected = buildBookEntities();

        when(bookRepository.findByTitleContaining(title))
                .thenReturn(expected);

        final var actual = bookStore.findByTitle(title);

        assertEquals(actual.size(), expected.size());

        verify(bookRepository).findByTitleContaining(title);
    }

    @Test
    void shouldFindByTitle_Empty() {
        final var title = randomAlphabetic(3, 10);
        when(bookRepository.findByTitleContaining(title))
                .thenReturn(Collections.emptyList());
        final var actual = bookStore.findByTitle(title);

        assertTrue(actual.isEmpty());
        verify(bookRepository).findByTitleContaining(title);
    }

    @Test
    void shouldFindByAuthor_OK() {
        final var author = randomAlphabetic(3, 10);
        final var expected = buildBookEntities();

        when(bookRepository.findByAuthorContaining(author))
                .thenReturn(expected);

        final var actual = bookStore.findByAuthor(author);

        assertEquals(actual.size(), expected.size());

        verify(bookRepository).findByAuthorContaining(author);
    }

    @Test
    void shouldFindByAuthor_Empty() {
        final var author = randomAlphabetic(3, 10);
        when(bookRepository.findByAuthorContaining(author))
                .thenReturn(Collections.emptyList());
        final var actual = bookStore.findByAuthor(author);

        assertTrue(actual.isEmpty());
        verify(bookRepository).findByAuthorContaining(author);
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

        verify(bookRepository).save(expected);
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

        verify(bookRepository).save(expected);
    }

    @Test
    void shouldDelete_OK() {
        final var book = buildBookEntity();
        bookStore.delete(book.getId());

        verify(bookRepository).deleteById(book.getId());
    }
}