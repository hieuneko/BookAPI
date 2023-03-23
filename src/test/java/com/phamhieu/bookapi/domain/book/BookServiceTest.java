package com.phamhieu.bookapi.domain.book;

import com.phamhieu.bookapi.domain.auth.AuthsProvider;
import com.phamhieu.bookapi.error.BadRequestException;
import com.phamhieu.bookapi.error.DomainException;
import com.phamhieu.bookapi.error.NotFoundException;
import com.phamhieu.bookapi.persistence.book.BookStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.phamhieu.bookapi.fakes.BookFakes.buildBook;
import static com.phamhieu.bookapi.fakes.BookFakes.buildBooks;
import static com.phamhieu.bookapi.fakes.UserAuthenticationTokenFakes.buildAdmin;
import static com.phamhieu.bookapi.fakes.UserAuthenticationTokenFakes.buildContributor;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookStore bookStore;

    @Mock
    private AuthsProvider authsProvider;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldFindAll_OK() {

        final var expected = buildBooks();

        when(bookStore.findAll())
                .thenReturn(expected);

        final var actual = bookService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getTitle(), actual.get(0).getTitle());
        assertEquals(expected.get(0).getAuthor(), actual.get(0).getAuthor());
        assertEquals(expected.get(0).getDescription(), actual.get(0).getDescription());
        assertEquals(expected.get(0).getCreatedAt(), actual.get(0).getCreatedAt());
        assertEquals(expected.get(0).getUpdatedAt(), actual.get(0).getUpdatedAt());
        assertEquals(expected.get(0).getImage(), actual.get(0).getImage());
        assertEquals(expected.get(0).getUserId(), actual.get(0).getUserId());

        verify(bookStore).findAll();
    }

    @Test
    void shouldFindById_OK() {
        final var expected = buildBook();
        when(bookStore.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        assertEquals(expected, bookService.findById(expected.getId()));
        verify(bookStore).findById(expected.getId());
    }

    @Test
    void shouFindById_Throw() {
        final var id = randomUUID();
        when(bookStore.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findById(id));
        verify(bookStore).findById(id);
    }

    @Test
    void shouldFind_OK() {
        final var expected = buildBooks();

        when(bookStore.find(anyString())).thenReturn(expected);

        final var actual = bookService.find(anyString());

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getTitle(), actual.get(0).getTitle());
        assertEquals(expected.get(0).getAuthor(), actual.get(0).getAuthor());
        assertEquals(expected.get(0).getDescription(), actual.get(0).getDescription());
        assertEquals(expected.get(0).getCreatedAt(), actual.get(0).getCreatedAt());
        assertEquals(expected.get(0).getUpdatedAt(), actual.get(0).getUpdatedAt());
        assertEquals(expected.get(0).getImage(), actual.get(0).getImage());
        assertEquals(expected.get(0).getUserId(), actual.get(0).getUserId());

        verify(bookStore).find(anyString());
    }

    @Test
    void shouldFind_Empty() {
        final var title = randomAlphabetic(3, 10);

        when(bookStore.find(title)).thenReturn(Collections.emptyList());

        assertTrue(bookService.find(title).isEmpty());
        verify(bookStore).find(title);
    }

    @Test
    void shouldCreate_OK() {
        final var book = buildBook();

        when(bookStore.create(any())).thenReturn(book);
        when(authsProvider.getCurrentAuthentication()).thenReturn(buildContributor());

        final var actual = bookService.create(book);

        assertEquals(book, actual);

        verify(bookStore).create(any());
    }

    @Test
    void shouldCreate_ThrowTitleNull() {
        final var expected = buildBook();
        expected.setTitle(null);

        assertThrows(BadRequestException.class, () -> bookService.create(expected));
    }

    @Test
    void shouldCreate_ThrowAuthorNull() {
        final var expected = buildBook();
        expected.setAuthor(null);

        assertThrows(BadRequestException.class, () -> bookService.create(expected));
    }

    @Test
    void shouldUpdateWithAdmin_OK() {
        final var book = buildBook();
        final var updatedBook = buildBook();
        updatedBook.setId(book.getId());

        when(bookStore.findById((book.getId()))).thenReturn(Optional.of(book));
        when(bookStore.update(book)).thenReturn(book);
        when(authsProvider.getCurrentRole()).thenReturn(buildAdmin().getRole());

        final var actual = bookService.update(book.getId(), updatedBook);

        actual.setCreatedAt(updatedBook.getCreatedAt());
        actual.setUpdatedAt(updatedBook.getUpdatedAt());

        assertEquals(updatedBook.getId().toString(), actual.getId().toString());
        assertEquals(updatedBook.getTitle(), actual.getTitle());
        assertEquals(updatedBook.getAuthor(), actual.getAuthor());
        assertEquals(updatedBook.getDescription(), actual.getDescription());
        assertEquals(updatedBook.getCreatedAt(), actual.getCreatedAt());
        assertEquals(updatedBook.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(updatedBook.getImage(), actual.getImage());
        assertEquals(updatedBook.getUserId().toString(), actual.getUserId().toString());

        verify(bookStore).update(book);
    }

    @Test
    void shouldUpdateWithContributor_OK() {
        final var book = buildBook();
        final var updatedBook = buildBook();
        updatedBook.setId(book.getId());

        when(bookStore.findById((book.getId()))).thenReturn(Optional.of(book));
        when(bookStore.update(book)).thenReturn(book);
        when(authsProvider.getCurrentAuthentication()).thenReturn(buildContributor());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());
        when(authsProvider.getCurrentRole()).thenReturn(buildContributor().getRole());

        final var userAuthenticationToken = authsProvider.getCurrentAuthentication();
        updatedBook.setUserId(userAuthenticationToken.getUserId());
        book.setUserId(userAuthenticationToken.getUserId());

        final var actual = bookService.update(book.getId(), updatedBook);

        actual.setCreatedAt(updatedBook.getCreatedAt());
        actual.setUpdatedAt(updatedBook.getUpdatedAt());

        assertEquals(updatedBook.getId().toString(), actual.getId().toString());
        assertEquals(updatedBook.getTitle(), actual.getTitle());
        assertEquals(updatedBook.getAuthor(), actual.getAuthor());
        assertEquals(updatedBook.getDescription(), actual.getDescription());
        assertEquals(updatedBook.getCreatedAt(), actual.getCreatedAt());
        assertEquals(updatedBook.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(updatedBook.getImage(), actual.getImage());
        assertEquals(updatedBook.getUserId().toString(), actual.getUserId().toString());

        verify(bookStore).update(book);
    }

    @Test
    void shouldUpdate_ThrowAccessRequired() {
        final var bookUpdate = buildBook();
        final var uuid = randomUUID();

        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());
        when(authsProvider.getCurrentRole()).thenReturn(buildContributor().getRole());

        assertThrows(DomainException.class, () -> bookService.update(uuid, bookUpdate));
    }

    @Test
    void shouldUpdate_NotFound() {
        final var bookUpdate = buildBook();
        final var uuid = randomUUID();

        when(authsProvider.getCurrentRole()).thenReturn(buildAdmin().getRole());
        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.update(uuid, bookUpdate));
        verify(bookStore).findById(uuid);
    }

    @Test
    void shouldUpdate_ThrowBadRequest() {
        final var id = randomUUID();
        final var expected = buildBook();
        expected.setTitle(null);

        when(authsProvider.getCurrentRole()).thenReturn(buildAdmin().getRole());

        assertThrows(BadRequestException.class, () -> bookService.update(id, expected));
    }

    @Test
    void shouldDeleteByIdWithAdmin_Ok() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentRole()).thenReturn(buildAdmin().getRole());

        bookService.delete(book.getId());
        verify(bookStore).delete(book.getId());
    }

    @Test
    void shouldDeleteByIdWithContributor_Ok() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentAuthentication()).thenReturn(buildContributor());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());
        when(authsProvider.getCurrentRole()).thenReturn(buildContributor().getRole());

        final var userAuthenticationToken = authsProvider.getCurrentAuthentication();
        book.setUserId(userAuthenticationToken.getUserId());

        bookService.delete(book.getId());
        verify(bookStore).delete(book.getId());
    }

    @Test
    void shouldDelete_ThrowAccessRequired() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());
        when(authsProvider.getCurrentRole()).thenReturn(buildContributor().getRole());

        assertThrows(DomainException.class, () -> bookService.delete(book.getId()));
        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldDeleteById_NotFound() {
        final var id = randomUUID();

        when(bookStore.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.delete(id));
        verify(bookStore).findById(id);
    }
}