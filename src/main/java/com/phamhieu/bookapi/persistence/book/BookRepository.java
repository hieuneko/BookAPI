package com.phamhieu.bookapi.persistence.book;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    List<BookEntity> findByTitle(final String bookTitle);

    List<BookEntity> findByAuthor(final String Author);
}
