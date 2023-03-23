package com.phamhieu.bookapi.persistence.role;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
}
