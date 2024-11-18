package com.movieapp.screenings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
class ScreeningsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScreeningsApplication.class, args);
    }

}
