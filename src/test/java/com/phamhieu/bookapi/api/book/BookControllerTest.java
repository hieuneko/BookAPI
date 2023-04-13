package com.phamhieu.bookapi.api.book;

import com.phamhieu.bookapi.api.AbstractControllerTest;
import com.phamhieu.bookapi.api.WithMockAdmin;
import com.phamhieu.bookapi.api.WithMockContributor;
import com.phamhieu.bookapi.domain.auth.AuthsProvider;
import com.phamhieu.bookapi.domain.book.Book;
import com.phamhieu.bookapi.domain.book.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.phamhieu.bookapi.fakes.BookFakes.buildBook;
import static com.phamhieu.bookapi.fakes.BookFakes.buildBooks;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@ExtendWith(SpringExtension.class)
class BookControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/books";

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthsProvider authsProvider;

    @BeforeEach
    void init() {
        when(authsProvider.getCurrentAuthentication())
                .thenCallRealMethod();
    }

    @Test
    @WithMockContributor
    void shouldFindAll_OK() throws Exception {
        final var books = buildBooks();

        when(bookService.findAll()).thenReturn(books);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(books.size()))
                .andExpect(jsonPath("$[0].id").value(books.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].title").value(books.get(0).getTitle()))
                .andExpect(jsonPath("$[0].author").value(books.get(0).getAuthor()))
                .andExpect(jsonPath("$[0].description").value(books.get(0).getDescription()))
                .andExpect(jsonPath("$[0].createdAt").value(books.get(0).getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(books.get(0).getUpdatedAt().toString()))
                .andExpect(jsonPath("$[0].image").value(books.get(0).getImage()))
                .andExpect(jsonPath("$[0].subtitle").value(books.get(0).getSubtitle()))
                .andExpect(jsonPath("$[0].publisher").value(books.get(0).getPublisher()))
                .andExpect(jsonPath("$[0].isbn13").value(books.get(0).getIsbn13()))
                .andExpect(jsonPath("$[0].price").value(books.get(0).getPrice()))
                .andExpect(jsonPath("$[0].year").value(books.get(0).getYear()))
                .andExpect(jsonPath("$[0].rating").value(books.get(0).getRating()))
                .andExpect(jsonPath("$[0].userId").value(books.get(0).getUserId().toString()));

        verify(bookService).findAll();
    }

    @Test
    @WithMockContributor
    void shouldFindById_OK() throws Exception {
        final var book = buildBook();

        when(bookService.findById(book.getId())).thenReturn(book);

        get(BASE_URL + "/" + book.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()))
                .andExpect(jsonPath("$.subtitle").value(book.getSubtitle()))
                .andExpect(jsonPath("$.publisher").value(book.getPublisher()))
                .andExpect(jsonPath("$.isbn13").value(book.getIsbn13()))
                .andExpect(jsonPath("$.price").value(book.getPrice()))
                .andExpect(jsonPath("$.year").value(book.getYear()))
                .andExpect(jsonPath("$.rating").value(book.getRating()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()));

        verify(bookService).findById(book.getId());
    }

    @Test
    @WithMockContributor
    void shouldFind_Ok() throws Exception {
        final var expected = buildBooks();

        when(bookService.find(anyString())).thenReturn(expected);

        final var actual = bookService.find(anyString());

        get(BASE_URL + "/search?keyword=" + anyString())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(actual.size()))
                .andExpect(jsonPath("$[0].id").value(actual.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].title").value(actual.get(0).getTitle()))
                .andExpect(jsonPath("$[0].author").value(actual.get(0).getAuthor()))
                .andExpect(jsonPath("$[0].description").value(actual.get(0).getDescription()))
                .andExpect(jsonPath("$[0].createdAt").value(actual.get(0).getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(actual.get(0).getUpdatedAt().toString()))
                .andExpect(jsonPath("$[0].image").value(actual.get(0).getImage()))
                .andExpect(jsonPath("$[0].subtitle").value(actual.get(0).getSubtitle()))
                .andExpect(jsonPath("$[0].publisher").value(actual.get(0).getPublisher()))
                .andExpect(jsonPath("$[0].isbn13").value(actual.get(0).getIsbn13()))
                .andExpect(jsonPath("$[0].price").value(actual.get(0).getPrice()))
                .andExpect(jsonPath("$[0].year").value(actual.get(0).getYear()))
                .andExpect(jsonPath("$[0].rating").value(actual.get(0).getRating()))
                .andExpect(jsonPath("$[0].userId").value(actual.get(0).getUserId().toString()));
    }

    @Test
    @WithMockContributor
    void shouldCreate_Ok() throws Exception {
        final var book = buildBook();

        when(bookService.create(any(Book.class))).thenReturn(book);

        post(BASE_URL, book)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()))
                .andExpect(jsonPath("$.subtitle").value(book.getSubtitle()))
                .andExpect(jsonPath("$.publisher").value(book.getPublisher()))
                .andExpect(jsonPath("$.isbn13").value(book.getIsbn13()))
                .andExpect(jsonPath("$.price").value(book.getPrice()))
                .andExpect(jsonPath("$.year").value(book.getYear()))
                .andExpect(jsonPath("$.rating").value(book.getRating()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()));
    }

    @Test
    @WithMockAdmin
    void shouldUpdate_Ok() throws Exception {
        final var book = buildBook();
        final var updatedBook = buildBook();
        updatedBook.setId(book.getId());

        when(bookService.update(eq(book.getId()), any(Book.class))).thenReturn(updatedBook);

        put(BASE_URL + "/" + book.getId(), updatedBook)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedBook.getId().toString()))
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(updatedBook.getAuthor()))
                .andExpect(jsonPath("$.description").value(updatedBook.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(updatedBook.getImage()))
                .andExpect(jsonPath("$.subtitle").value(updatedBook.getSubtitle()))
                .andExpect(jsonPath("$.publisher").value(updatedBook.getPublisher()))
                .andExpect(jsonPath("$.isbn13").value(updatedBook.getIsbn13()))
                .andExpect(jsonPath("$.price").value(updatedBook.getPrice()))
                .andExpect(jsonPath("$.year").value(updatedBook.getYear()))
                .andExpect(jsonPath("$.rating").value(updatedBook.getRating()))
                .andExpect(jsonPath("$.userId").value(updatedBook.getUserId().toString()));
    }

    @Test
    @WithMockAdmin
    void shouldUploadImage_OK() throws Exception {
        final var id = randomUUID();
        final var bytes = "abc".getBytes();
        final var file = new MockMultipartFile("file", "image.png", "image/png", bytes);

        postFile(BASE_URL + "/" + id + "/image", file)
                .andExpect(status().isOk());

        verify(bookService).uploadImage(id, bytes);
    }

    @Test
    @WithMockAdmin
    void shouldDeleteById_Ok() throws Exception {
        final var book = buildBook();

        delete(BASE_URL + "/" + book.getId())
                .andExpect(status().isOk());

        verify(bookService).delete(book.getId());
    }
}