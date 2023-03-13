package com.phamhieu.bookapi.domain.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class User {

    private UUID id;
    private UUID roleId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String avatar;
}
