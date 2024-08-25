package com.movieapp.users.domain.service;

import com.movieapp.users.domain.entity.RoleType;
import com.movieapp.users.domain.repository.RoleRepository;
import com.movieapp.users.testcontainers.TestContainersBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RoleInitializerTest extends TestContainersBase {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleInitializer roleInitializer;

    @Test
    @DisplayName("Should load ROLE_USER on Application boot")
    @Order(1)
    void shouldLoadUserRoleWhenApplicationStarts() {
        assertTrue(roleRepository.existsByRoleType(RoleType.USER));
    }

    @Test
    @DisplayName("Should load ROLE_ADMIN on Application boot")
    @Order(2)
    void shouldLoadAdminRoleWhenApplicationStarts() {
        assertTrue(roleRepository.existsByRoleType(RoleType.ADMIN));
    }

    @Test
    @DisplayName("Should load ROLE_MODERATOR on Application boot")
    @Order(3)
    void shouldLoadModeratorRoleWhenApplicationStarts() {
        assertTrue(roleRepository.existsByRoleType(RoleType.MODERATOR));
    }

    @Test
    @DisplayName("Should not load ROLE on Application boot if it already exists")
    @Order(4)
    void shouldNotCreateRoleIfItAlreadyExists() {
        assertTrue(roleRepository.existsByRoleType(RoleType.USER));
        assertTrue(roleRepository.existsByRoleType(RoleType.ADMIN));
        assertTrue(roleRepository.existsByRoleType(RoleType.MODERATOR));

        roleInitializer.initializeRoles();
        assertDoesNotThrow(() -> roleInitializer.initializeRoles());
    }

}
