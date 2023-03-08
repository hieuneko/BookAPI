package com.phamhieu.bookapi.api.book;

import com.phamhieu.bookapi.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.phamhieu.bookapi.api.book.BookDTOMapper.*;


@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "find all books")
    @GetMapping
    public List<BookDTO> findAllBook() {
        return toBookDTOs(bookService.findAllBook());
    }

    @Operation(summary = "find book by id")
    @GetMapping("{bookId}")
    public BookDTO findBookById(@PathVariable(name = "bookId") UUID bookId) {
        return toBookDTO(bookService.findBookById(bookId));
    }

    @Operation(summary = "find book by title")
    @GetMapping("title/{bookTitle}")
    public List<BookDTO> findBookByTitle(@PathVariable(name = "bookTitle") String title) {
        return toBookDTOs(bookService.findBookByTitle(title));
    }

    @Operation(summary = "find book by author")
    @GetMapping("author/{author}")
    public List<BookDTO> findBookByAuthor(@PathVariable(name = "author") String author) {
        return toBookDTOs(bookService.findBookByAuthor(author));
    }

    @Operation(summary = "Add new book")
    @PostMapping
    public BookDTO addUser(@RequestBody BookDTO bookDTO) {
        return toBookDTO(bookService.addBook(toBook(bookDTO)));
    }

    @Operation(summary = "Update book information")
    @PatchMapping("{bookId}")
    public BookDTO updateBook(@RequestBody BookDTO bookDTO, @PathVariable(name = "bookId") UUID bookId) {
        return toBookDTO(bookService.updateBook(toBook(bookDTO), bookId));
    }

    @Operation(summary = "Delete book by id")
    @DeleteMapping("{bookId}")
    public String deleteBook(@PathVariable(name = "bookId") UUID bookId) {
        try{
            bookService.deleteBook(bookId);
            return "success";
        }
        catch (Exception e) {
            return "false";
        }
    }
}
