package com.phamhieu.bookapi.persistence.book;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private String author;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private String image;

    private UUID userId;
}
