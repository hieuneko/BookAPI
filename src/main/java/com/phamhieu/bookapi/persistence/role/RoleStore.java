package com.phamhieu.bookapi.persistence.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleStore {

    private final RoleRepository roleRepository;

    public String findRoleName(final UUID roleId) {
        return "ROLE_" + roleRepository.findRoleName(roleId);
    }
}
