package com.movieapp.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
class UserController {
    private final UserService userService;

    @PostMapping
    ResponseEntity<UserDTO> register(@RequestBody @Valid UserRegisterRequest registerRequest) {
        log.info("Registering user: {}", registerRequest);
        UserDTO user = userService.register(registerRequest);
        log.info("Registered user DTO: {}", user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        log.info("Finding user by id: {}", id);
        UserDTO user = userService.findById(id);
        log.info("Found user DTO: {}", user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") Long id) {
        log.info("Deleting user by id: {}", id);
        userService.deleteById(id);
    }
}
