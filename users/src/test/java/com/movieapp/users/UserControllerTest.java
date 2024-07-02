package com.movieapp.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerTest {
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
        UserDTO user = spec.expectBody(UserDTO.class)
                .returnResult()
                .getResponseBody();
        validateUser(request, user);
    }

    @ParameterizedTest
    @CsvSource({
            "testuseruser.com, password123, Michael, Protas",
            "homersimpson@buzz.com, ho, Homer, Simpson"
    })
    void shouldRejectInvalidCredentials(String email, String password, String firstName, String lastName) {
        UserRegisterRequest request = new UserRegisterRequest(email, password, firstName, lastName);
        webClient.getResponseSpecForRequestBody(request)
                .expectStatus()
                .isBadRequest(); // todo: get response's body
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

    private void validateUser(UserRegisterRequest request, UserDTO user) {
        assertNotNull(user);
        assertNotNull(user.email());
        assertEquals(request.email(), user.email());
    }

}
