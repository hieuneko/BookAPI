package com.phamhieu.bookapi.api.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class BookRequestDTO {
    private String title;
    private String author;
    private String description;
    private String image;
    private UUID userId;
}
