package com.movieapp.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
                .isBadRequest();
    }

    private void validateUser(UserRegisterRequest request, UserDTO user) {
        assertNotNull(user);
        assertNotNull(user.email());
        assertEquals(request.email(), user.email());
    }

}
