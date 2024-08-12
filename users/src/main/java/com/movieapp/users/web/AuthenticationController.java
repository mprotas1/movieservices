package com.movieapp.users.web;

import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserRegisterRequest;
import com.movieapp.users.domain.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/auth")
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    ResponseEntity<UserDTO> register(@RequestBody UserRegisterRequest request) {
        log.info("Registering user with email address: {}", request.email());
        UserDTO user = authenticationService.register(request);
        return ResponseEntity.ok(user);
    }

}
