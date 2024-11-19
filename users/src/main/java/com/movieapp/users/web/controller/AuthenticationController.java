package com.movieapp.users.web.controller;

import com.movieapp.users.web.dto.UserAuthenticationResponse;
import com.movieapp.users.web.dto.UserLoginRequest;
import com.movieapp.users.web.dto.UserRegisterRequest;
import com.movieapp.users.domain.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Slf4j
class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    ResponseEntity<UserAuthenticationResponse> register(@RequestBody UserRegisterRequest request) {
        log.info("Registering user with email address: {}", request.email());
        UserAuthenticationResponse apiResponse = authenticationService.register(request);
        return ResponseEntity.created(getLocation(apiResponse)).body(apiResponse);
    }

    @PostMapping
    ResponseEntity<UserAuthenticationResponse> authenticate(@RequestBody UserLoginRequest request) {
        log.info("Authenticating user with email address: {}", request.email());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    private URI getLocation(UserAuthenticationResponse response) {
        return URI.create("/users/" + response.user().id());
    }

}
