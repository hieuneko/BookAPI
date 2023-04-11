package com.phamhieu.bookapi.api.book;

import com.phamhieu.bookapi.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.phamhieu.bookapi.api.book.BookDTOMapper.*;


@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Find all books")
    @GetMapping
    public List<BookResponseDTO> findAll() {
        return toBookDTOs(bookService.findAll());
    }

    @Operation(summary = "Find book by id")
    @GetMapping("{bookId}")
    public BookResponseDTO findById(@PathVariable UUID bookId) {
        return toBookResponseDTO(bookService.findById(bookId));
    }

    @Operation(summary = "Find book by title, author, description, subtitle, publisher or isbn13 is containing")
    @GetMapping("search")
    public List<BookResponseDTO> find(@RequestParam String keyword) {
        return toBookDTOs(bookService.find(keyword));
    }

    @Operation(summary = "Add new book")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @PostMapping
    public BookResponseDTO create(@RequestBody BookRequestDTO bookRequestDTO) {
        return toBookResponseDTO(bookService.create(toBook(bookRequestDTO)));
    }

    @Operation(summary = "Update book information")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @PutMapping("{bookId}")
    public BookResponseDTO update(@PathVariable UUID bookId, @RequestBody BookRequestDTO bookRequestDTO) {
        return toBookResponseDTO(bookService.update(bookId, toBook(bookRequestDTO)));
    }

    @Operation(summary = "Delete book by id")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @DeleteMapping("{bookId}")
    public void delete(@PathVariable UUID bookId) {
        bookService.delete(bookId);
    }
}
