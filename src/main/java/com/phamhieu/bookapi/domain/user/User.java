package com.phamhieu.bookapi.domain.user;

import com.phamhieu.bookapi.domain.role.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.util.UUID;

@Builder
@Getter
@Setter
public class User {

    private UUID id;
    private UUID roleId;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private boolean enabled;
    private String avatar;
}
