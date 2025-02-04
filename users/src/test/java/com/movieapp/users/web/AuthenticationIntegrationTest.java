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
import com.movieapp.users.web.exception.RestExceptionMessage;
import lombok.With;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithAnonymousUser
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
    @DisplayName("Should successfully register a valid user")
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
    }

    @Test
    @DisplayName("Should not register user with invalid formatted e-mail")
    void shouldFailToRegisterUserWithInvalidEmail() {
        UserRegisterRequest invalidEmailRequest = new UserRegisterRequest("invalidmail", "password123", "John", "Doe");
        HttpEntity<UserRegisterRequest> request = new HttpEntity<>(invalidEmailRequest, headers);

        ResponseEntity<RestExceptionMessage> response = restTemplate.postForEntity(BASIC_API_URL + "/register", request, RestExceptionMessage.class);
        RestExceptionMessage body = response.getBody();

        validateRestExceptionMessage(response, body);
    }

    @Test
    @DisplayName("Should not register user when there is a existing one with same e-mail address")
    void shouldFailToRegisterUserWithAlreadyExistingEmailAddress() {
        // arrange
        HttpEntity<UserRegisterRequest> request = new HttpEntity<>(registerRequest, headers);
        authenticationService.register(registerRequest);

        // act
        ResponseEntity<RestExceptionMessage> failedResponse = restTemplate.postForEntity(BASIC_API_URL + "/register",
                                                                                             request,
                                                                                             RestExceptionMessage.class);
        RestExceptionMessage failedBody = failedResponse.getBody();

        // assert
        validateRestExceptionMessage(failedResponse, failedBody);
    }

    @Test
    @DisplayName("Should successfully login valid user")
    void shouldSuccessfullyLoginValidUser() {
        // arrange
        HttpEntity<UserLoginRequest> request = new HttpEntity<>(loginRequest, headers);
        UserAuthenticationResponse register = authenticationService.register(registerRequest);

        // act
        ResponseEntity<UserAuthenticationResponse> response = restTemplate.postForEntity(BASIC_API_URL, request, UserAuthenticationResponse.class);
        UserAuthenticationResponse responseBody = response.getBody();

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertFalse(responseBody.token().isEmpty());
    }

    // 4. Test invalid login for non-existing user

    // 5. Test invalid login for incorrect password

    private void initBasicHeaders() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private void validateRestExceptionMessage(ResponseEntity<RestExceptionMessage> failedResponse, RestExceptionMessage failedBody) {
        assertTrue(failedResponse.getStatusCode().is4xxClientError());
        assertEquals(HttpStatus.BAD_REQUEST, failedResponse.getStatusCode());
        assertNotNull(failedBody);
        assertNotNull(failedBody.message());
        assertEquals(HttpStatus.BAD_REQUEST.value(), failedBody.statusCode());
        assertNotNull(failedBody.timeStamp());
    }

}
