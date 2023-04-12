package com.phamhieu.bookapi.persistence.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query(value = "SELECT b " +
            "FROM BookEntity b " +
            "WHERE b.title LIKE %:keyword% " +
            "OR b.author LIKE %:keyword% " +
            "OR b.description LIKE %:keyword% " +
            "OR b.subtitle LIKE %:keyword% " +
            "OR b.publisher LIKE %:keyword% " +
            "OR b.isbn13 LIKE %:keyword%", nativeQuery = true)
    List<BookEntity> findByKeyword(final String keyword);

    Optional<BookEntity> findByIsbn13(final String isbn13);
}
