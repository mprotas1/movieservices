package com.movieapp.users;

import com.movieapp.users.domain.UserRepository;
import com.movieapp.users.domain.UserService;
import com.movieapp.users.testcontainers.TestContainersBase;
import com.movieapp.users.web.RestExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserIntegrationTest extends TestContainersBase {
    private final String BASE_URL = "/users/";

    @Autowired
    private UsersTestClient webClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    private final UserRegisterRequest validRequest = new UserRegisterRequest("testuser@gmail.com",
            "pass123",
            "Test",
            "User");

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource({
      "testuser@user.com, password123, Michael, Protas",
      "homersimpson@buzz.com, homieatdonut12, Homer, Simpson"
    })
    void shouldCorrectlyCreateUser(String email, String password, String firstName, String lastName) {
        UserRegisterRequest request = new UserRegisterRequest(email, password, firstName, lastName);
        ResponseSpec spec = webClient.getResponseSpecForRequestBody(request);
        EntityExchangeResult<UserDTO> entityExchangeResult = spec.expectBody(UserDTO.class)
                .returnResult();

        assertNotNull(entityExchangeResult.getResponseHeaders().get(HttpHeaders.LOCATION));
        String expectedLocationHeader = BASE_URL + entityExchangeResult.getResponseBody().id();
        assertEquals(expectedLocationHeader, entityExchangeResult.getResponseHeaders().getLocation().getPath());
        validateUser(request, entityExchangeResult.getResponseBody());
    }

    @ParameterizedTest
    @CsvSource({
            "testuseruser.com, password123, Michael, Protas",
            "homersimpson@buzz.com, ho, Homer, Simpson"
    })
    void shouldRejectInvalidCredentials(String email, String password, String firstName, String lastName) {
        UserRegisterRequest request = new UserRegisterRequest(email, password, firstName, lastName);
        FluxExchangeResult<String> restExceptionMessageEntityExchangeResult = webClient.getResponseSpecForRequestBody(request)
                .expectStatus()
                .isBadRequest()
                .returnResult(String.class);

        String exceptionResult = restExceptionMessageEntityExchangeResult.getResponseBody().blockFirst();
        assertNotNull(exceptionResult);
    }

    @Test
    void shouldFindUserById() {
        UserRegisterRequest request = new UserRegisterRequest("testuser@gmail.com", "pass123", "Test", "User");
        UserDTO register = userService.register(request);
        Long id = register.id();
        EntityExchangeResult<UserDTO> userDTOEntityExchangeResult = webClient.requestSpecForGetById(id.intValue())
                .expectStatus()
                .isOk()
                .expectBody(UserDTO.class)
                .returnResult();

        UserDTO userDTO = userDTOEntityExchangeResult.getResponseBody();
        validateUser(request, userDTO);
    }

    @Test
    void shouldThrowWhenFindingNonExistingId() {
        UserDTO register = userService.register(validRequest);
        int id = register.id().intValue() + 10000;

        webClient.requestSpecForGetById(id)
                .expectStatus()
                .isBadRequest()
                .expectBody(RestExceptionMessage.class)
                .returnResult();
    }

    @Test
    void shouldDeleteUserById() {
        UserDTO user = userService.register(validRequest);
        Long id = user.id();
        webClient.getDeleteWithIdResponseSpec(id)
                .expectStatus()
                .isNoContent();
    }

    @Test
    void shouldThrowWhenDeleteNonExistingId() {
        long userCount = userRepository.count() + 5000;
        webClient.getDeleteWithIdResponseSpec(userCount)
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldFindAllUsers() {
        userService.register(validRequest);
        EntityExchangeResult<List<UserDTO>> listEntityExchangeResult = webClient.newWebClient()
                .get().uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(UserDTO.class)
                .value(users -> {
                    assertNotNull(users);
                    assertEquals(1, users.size());
                    assertThat(users).isNotEmpty();
                })
                .returnResult();

        assertEquals(HttpStatus.OK, listEntityExchangeResult.getStatus());
    }

    private void validateUser(UserRegisterRequest request, UserDTO user) {
        assertNotNull(user);
        assertNotNull(user.id());
        assertNotNull(user.email());
        assertEquals(request.email(), user.email());
        assertEquals(1, user.roles().size());
    }

}
