package com.movieapp.users.testcontainers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DBInitializationTest extends TestContainersBase {
    @Test
    void databaseIsInitialized() {
        assertNotNull(postgres);
        assertNotNull(postgres.getTestQueryString());
        assertTrue(postgres.isCreated());
    }
}
