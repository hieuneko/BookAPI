package com.phamhieu.bookapi.api.book;

import org.junit.jupiter.api.Test;

import static com.phamhieu.bookapi.api.book.BookDTOMapper.*;
import static com.phamhieu.bookapi.fakes.BookFakes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class BookResponseDTOMapperTest {

    @Test
    void shouldToBookDTO_OK() {
        final var book = buildBook();
        final var bookResponseDTO = toBookResponseDTO(book);

        assertEquals(book.getId(), bookResponseDTO.getId());
        assertEquals(book.getTitle(), bookResponseDTO.getTitle());
        assertEquals(book.getAuthor(), bookResponseDTO.getAuthor());
        assertEquals(book.getDescription(), bookResponseDTO.getDescription());
        assertEquals(book.getImage(), bookResponseDTO.getImage());
        assertEquals(book.getUserId(), bookResponseDTO.getUserId());
    }

    @Test
    void shouldToBookDTOs_OK() {
        final var books = buildBooks();

        final var bookDTOs = toBookDTOs(books);
        assertEquals(books.size(), bookDTOs.size());
    }

    @Test
    void shouldToBook_OK() {
        final var bookRequestDTO = buildBookRequestDTO();
        final var book = toBook(bookRequestDTO);

        assertEquals(bookRequestDTO.getTitle(), book.getTitle());
        assertEquals(bookRequestDTO.getAuthor(), book.getAuthor());
        assertEquals(bookRequestDTO.getDescription(), book.getDescription());
        assertEquals(bookRequestDTO.getImage(), book.getImage());
    }
}