package com.movieapp.users;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {

    @GetMapping
    ResponseEntity<String> helloFromCustomers() {
        return ResponseEntity.ok("Hello from Customers!");
    }

    @PostMapping
    ResponseEntity<UserResponse> register(@RequestBody @Valid User user) {
        return ResponseEntity.ok(new UserResponse(1L, "some@email.com"));
    }
}
