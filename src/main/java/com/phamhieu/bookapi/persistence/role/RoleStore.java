package com.phamhieu.bookapi.persistence.role;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RoleStore {

    public String getRoleById(final UUID uuid) {
        if (uuid.equals(UUID.fromString("921210b1-dfe2-425a-8480-01f6f01be15e"))) {
            return "ROLE_ADMIN";
        }

        if (uuid.equals(UUID.fromString("8b929657-6884-48b5-a25a-5b223acbdfcf"))) {
            return "ROLE_CONTRIBUTOR";
        }

        return null;
    }
}
