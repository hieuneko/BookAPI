package com.phamhieu.bookapi.api.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ProfileRequestDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String avatar;
}
