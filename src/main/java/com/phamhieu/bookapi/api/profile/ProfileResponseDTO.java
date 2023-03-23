package com.phamhieu.bookapi.api.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ProfileResponseDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String avatar;
}
