package com.phamhieu.bookapi.persistence.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleStoreTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleStore roleStore;

    @Test
    void shouldFindName_OK() {
        final UUID roleId = randomUUID();
        final String roleName = randomAlphabetic(3, 10);
        when(roleRepository.findRoleName(roleId))
                .thenReturn(roleName);

        assertEquals(roleName, roleStore.findRoleName(roleId));
        verify(roleRepository).findRoleName(roleId);
    }

    @Test
    void shouldFindById_Empty() {
        final UUID roleId = randomUUID();
        final String roleName = "";
        when(roleRepository.findRoleName(roleId))
                .thenReturn(roleName);

        assertTrue(roleStore.findRoleName(roleId).isEmpty());
        verify(roleRepository).findRoleName(roleId);
    }

    @Test
    void shouldRoleId_OK() {
        final UUID roleId = randomUUID();
        final RoleEntity roleEntity = new RoleEntity(roleId, "CONTRIBUTOR");
        final String roleName = randomAlphabetic(3, 10);
        when(roleRepository.findByName(roleName))
                .thenReturn(Optional.of(roleEntity));

        assertEquals(roleId, roleStore.findIdByName(roleName));
        verify(roleRepository).findByName(roleName);
    }

    @Test
    void shouldFindRoleId_Empty() {
        final String roleName = randomAlphabetic(3, 10);
        when(roleRepository.findByName(roleName))
                .thenReturn(null);

        assertNull(roleStore.findIdByName(roleName));
        verify(roleRepository).findByName(roleName);
    }


}