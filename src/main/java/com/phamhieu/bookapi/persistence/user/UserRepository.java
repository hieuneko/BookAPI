package com.phamhieu.bookapi.persistence.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(final String username);

    List<UserEntity> findByUsernameOrFirstNameOrLastName(final String username, final String firstName, final String lastName);
}
