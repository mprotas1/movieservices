package com.movieapp.users;

import com.movieapp.users.domain.CredentialsUserService;
import com.movieapp.users.domain.Role;
import com.movieapp.users.domain.RoleType;
import com.movieapp.users.domain.UserRepository;
import com.movieapp.users.testcontainers.TestContainersBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RoleIntegrationTest extends TestContainersBase {
    @Autowired
    private UsersTestClient usersTestClient;

    @Autowired
    private CredentialsUserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUserWithDefaultUserRole() throws MalformedURLException {
        String userEndpointPath = "/users/";
        UserRegisterRequest request = new UserRegisterRequest("validemail@gmail.com", "somepassword123", "Michał", "Protas");

        EntityExchangeResult<UserDTO> userDTOEntityExchangeResult = usersTestClient.getResponseSpecForRequestBody(request)
                .expectBody(UserDTO.class)
                .returnResult();
        UserDTO userDTO = userDTOEntityExchangeResult.getResponseBody();
        URI locationHeader = userDTOEntityExchangeResult.getResponseHeaders().getLocation();

        assertNotNull(userDTO.roles());
        assertNotNull(locationHeader);
        assertEquals(userDTO.roles().size(), 1);
        assertEquals(userEndpointPath + userDTO.id().toString(), userDTOEntityExchangeResult.getResponseHeaders().getLocation().toURL().getPath());
        assertTrue(userDTO.roles().stream().map(Role::getRoleType).toList().contains(RoleType.USER));
        assertEquals(HttpStatus.CREATED, userDTOEntityExchangeResult.getStatus());
    }

    @Test
    void shouldFindAllUsersByRole() {
        UserRegisterRequest firstRequest = new UserRegisterRequest("somemail1@gmail.com", "test", "test", "test");
        userService.register(firstRequest);
        UserRegisterRequest secondRequest = new UserRegisterRequest("somemail2@gmail.com", "test", "test", "test");
        userService.register(secondRequest);

        EntityExchangeResult<List<UserDTO>> listEntityExchangeResult = usersTestClient.newWebClient()
                .get()
                .uri("/users")
                .exchange()
                .expectBody(new ParameterizedTypeReference<List<UserDTO>>() {})
                .returnResult();

        HttpStatus status = (HttpStatus) listEntityExchangeResult.getStatus();
        List<UserDTO> userDTOs = listEntityExchangeResult.getResponseBody();

        assertNotNull(userDTOs);
        assertEquals(HttpStatus.OK, status);
        assertEquals(2, userDTOs.size());
    }

}
