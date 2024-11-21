package com.movieapp.users.testcontainers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application.yml")
@Testcontainers
public abstract class TestContainersBase {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("usersdb")
            .withUsername("user")
            .withPassword("secret")
            .withExposedPorts(5432);

    public TestContainersBase() {
        postgres.start();
        System.setProperty("DB_URL", postgres.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgres.getUsername());
        System.setProperty("DB_PASSWORD", postgres.getPassword());
    }

    @AfterAll
    public static void tearDown() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

}
