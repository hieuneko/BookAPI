package com.phamhieu.bookapi.api.book;

import com.phamhieu.bookapi.domain.book.Book;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

public class BookDTOMapper {
    public static BookResponseDTO toBookResponseDTO(final Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .image(book.getImage())
                .userId(book.getUserId())
                .build();
    }

    public static List<BookResponseDTO> toBookDTOs(final List<Book> books) {
        return emptyIfNull(books)
                .stream()
                .map(BookDTOMapper::toBookResponseDTO)
                .toList();
    }

    public static Book toBook(final BookRequestDTO bookRequestDTO) {
        return Book.builder()
                .title(bookRequestDTO.getTitle())
                .author(bookRequestDTO.getAuthor())
                .description(bookRequestDTO.getDescription())
                .image(bookRequestDTO.getImage())
                .userId(bookRequestDTO.getUserId())
                .build();
    }
}
