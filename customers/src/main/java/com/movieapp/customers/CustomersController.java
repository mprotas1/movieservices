package com.movieapp.customers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class CustomersController {

    @GetMapping
    ResponseEntity<String> helloFromCustomers() {
        return ResponseEntity.ok("Hello from Customers!");
    }

}
