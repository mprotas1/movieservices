package com.movieapp.users.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.domain.repository.UserRepository;
import com.movieapp.users.domain.service.AuthenticationService;
import com.movieapp.users.domain.service.TokenService;
import com.movieapp.users.domain.service.UserService;
import com.movieapp.users.testcontainers.TestContainersBase;
import com.movieapp.users.web.dto.UserAuthenticationResponse;
import com.movieapp.users.web.dto.UserLoginRequest;
import com.movieapp.users.web.dto.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest extends TestContainersBase {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userCrudService;
    @Autowired
    AuthenticationService authenticationService;

    private final String BASIC_API_URL = "/users/auth";
    private UserRegisterRequest registerRequest;
    private UserLoginRequest loginRequest;
    private HttpHeaders headers;

    @BeforeEach
    void initialize() {
        registerRequest = new UserRegisterRequest("validemail@gmail.com", "someVeryHardPassword123@", "John", "Doe");
        loginRequest = new UserLoginRequest(registerRequest.email(), registerRequest.password());
        initBasicHeaders();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should successfully register valid user")
    void shouldSuccessfullyRegisterUser() {
        // arrange
        HttpEntity<UserRegisterRequest> request = new HttpEntity<>(registerRequest, headers);

        // act
        ResponseEntity<UserAuthenticationResponse> response = restTemplate.postForEntity(BASIC_API_URL + "/register", request, UserAuthenticationResponse.class);
        UserAuthenticationResponse responseBody = response.getBody();
        URI location = response.getHeaders().getLocation();

        // assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(responseBody);
        assertFalse(responseBody.token().isEmpty());
        assertNotNull(location);
        assertEquals("/users/" + responseBody.user().id(), location.getPath());
        assertNotNull(responseBody.user());
        assertTrue(userRepository.findByEmail(responseBody.user().email()).isPresent());
    }

    @Test
    @DisplayName("Should fail to register user with invalid email")
    void shouldFailToRegisterUserWithInvalidEmail() {
        UserRegisterRequest invalidEmailRequest = new UserRegisterRequest("invalidmail", "password123", "John", "Doe");
        HttpEntity<UserRegisterRequest> request = new HttpEntity<>(invalidEmailRequest, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(BASIC_API_URL + "/register", request, Map.class);
        System.out.println(response.toString());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Should successfully login valid user")
    void shouldSuccessfullyLoginValidUser() {
        // arrange
        HttpEntity<UserLoginRequest> request = new HttpEntity<>(loginRequest, headers);
        UserAuthenticationResponse register = authenticationService.register(registerRequest);
        User registered = userRepository.findById(register.user().id()).orElseThrow();

        // act
        ResponseEntity<UserAuthenticationResponse> response = restTemplate.postForEntity(BASIC_API_URL, request, UserAuthenticationResponse.class);
        UserAuthenticationResponse responseBody = response.getBody();

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertFalse(responseBody.token().isEmpty());
        assertNotNull(responseBody.user());
        assertEquals(registered.getId(), responseBody.user().id());
        assertTrue(tokenService.validate(responseBody.token(), userRepository.findById(responseBody.user().id()).orElseThrow()));
    }

    // 4. Test invalid login for non-existing user

    // 5. Test invalid login for incorrect password

    private void initBasicHeaders() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

}
