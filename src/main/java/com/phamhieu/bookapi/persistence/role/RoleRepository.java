package com.phamhieu.bookapi.persistence.role;

import com.phamhieu.bookapi.persistence.user.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<UserEntity, UUID> {
    @Query("SELECT c from RoleEntity c ")
    String getRoleById(final UUID roleId);
}
