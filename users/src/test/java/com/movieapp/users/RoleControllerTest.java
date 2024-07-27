package com.movieapp.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RoleControllerTest extends TestContainersBase {
    @Autowired
    private UsersTestClient webClient;

    @Test
    void shouldCreateUserWithDefaultUserRole() {
        // 1. Create the User
        UserRegisterRequest request = new UserRegisterRequest("validemail@gmail.com", "somepassword123", "Michał", "Protas");

        // 2. Get the Response
        EntityExchangeResult<UserDTO> userDTOEntityExchangeResult = webClient.getResponseSpecForRequestBody(request).expectBody(UserDTO.class).returnResult();
        UserDTO userDTO = userDTOEntityExchangeResult.getResponseBody();
        String locationHeader = Objects.requireNonNull(userDTOEntityExchangeResult.getResponseHeaders().getLocation()).toString();

        // 3. Validate Role value
        assert userDTO != null;
        List<Role> roles = userDTO.roles();
        assertNotNull(locationHeader);
        assertEquals(roles.size(), 1);
        assertTrue(roles.stream().map(Role::getRoleName).toList().contains(RoleType.USER));
    }

}
