package com.phamhieu.bookapi.api.book;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
public class BookDTO {
    private UUID id;
    private String title;
    private String author;
    private String description;
    private String image;
    private UUID userId;
}
