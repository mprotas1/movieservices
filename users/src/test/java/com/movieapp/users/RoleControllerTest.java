package com.movieapp.users;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleControllerTest {

    @Test
    void shouldCreateUserWithDefaultUserRole() {
        // 1. Create the User
        UserRegisterRequest request = new UserRegisterRequest("validemail@gmail.com", "somepassword123", "Michał", "Protas");

        // 2. Get the Response


        // 3.
    }
}
