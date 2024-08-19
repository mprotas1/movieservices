package com.movieapp.users.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleInitializerTest {

    @Test
    @DisplayName("Should load ROLE_USER on Application boot")
    void shouldLoadUserRoleWhenApplicationStarts() {
        // Given the application is running
        // When the application starts
        // Then the user roles should be loaded/saved in the database
    }

    @Test
    @DisplayName("Should load ROLE_ADMIN on Application boot")
    void shouldLoadAdminRoleWhenApplicationStarts() {
        // Given the application is running
        // When the application starts
        // Then the admin roles should be loaded/saved in the database
    }

    @Test
    @DisplayName("Should load ROLE_MODERATOR on Application boot")
    void shouldLoadModeratorRoleWhenApplicationStarts() {
        // Given the application is running
        // When the application starts
        // Then the moderator roles should be loaded/saved in the database
    }

    @Test
    @DisplayName("Should not load ROLE on Application boot if it already exists")
    void shouldNotLoadRoleWhenApplicationStartsIfItAlreadyExists() {
        // Given the application is running
        // When the application starts
        // Then the roles should not be loaded/saved in the database if they already exist
    }

}
