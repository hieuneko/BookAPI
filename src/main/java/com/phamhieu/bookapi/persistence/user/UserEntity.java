package com.phamhieu.bookapi.persistence.user;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID roleId;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private boolean enabled;

    private String avatar;
}
