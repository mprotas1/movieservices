package com.movieapp.cinemas.testcontainers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application.yml")
public abstract class Containers {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Container
    public static final MySQLContainer<?> mySqlContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("cinemasdb")
            .withUsername("user")
            .withPassword("secret")
            .withExposedPorts(3306);

    public Containers() {
        log.info("Starting MySQL container...");
        long start = System.currentTimeMillis();
        mySqlContainer.start();
        log.debug("MySQL container started in {} ms", System.currentTimeMillis() - start);
    }

    @DynamicPropertySource
    static void mySqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySqlContainer::getUsername);
        registry.add("spring.datasource.password", mySqlContainer::getPassword);
    }

}