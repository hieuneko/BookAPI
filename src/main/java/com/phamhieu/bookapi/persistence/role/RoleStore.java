package com.phamhieu.bookapi.persistence.role;

import com.phamhieu.bookapi.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleStore {

    private final RoleRepository roleRepository;

    public String findRoleName(final UUID roleId) {
        return roleRepository.findRoleName(roleId);
    }

    public UUID findIdByName(final String roleName) {
        return roleRepository.findByName(roleName)
                .map(RoleEntity::getId)
                .orElseThrow(() -> new NotFoundException("Role not found - " + roleName));
    }
}
