package com.movieapp.users.web.controller;

import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        log.info("Finding user by id: {}", id);
        UserDTO user = userService.findById(id);
        log.info("Found user DTO: {}", user);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    ResponseEntity<List<UserDTO>> findAll() {
        log.info("Finding all users");
        var users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") Long id) {
        log.info("Deleting user by id: {}", id);
        userService.deleteById(id);
    }

    private URI buildRegisterUri(UserDTO user) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.id()).toUri();
    }

}
