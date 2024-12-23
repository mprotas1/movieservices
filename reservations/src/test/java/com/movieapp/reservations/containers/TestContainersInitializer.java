package com.movieapp.reservations.containers;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

class TestContainersInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("postgres")
            .withPassword("password")
            .withDatabaseName("reservations")
            .withExposedPorts(5432);

    static {
        postgres.start();
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {

    }
}
