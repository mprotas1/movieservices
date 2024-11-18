package com.movieapp.cinemas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
class CinemasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemasApplication.class, args);
    }

}
