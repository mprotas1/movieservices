package com.movieapp.cinemas.web;

import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import com.movieapp.cinemas.testcontainers.Containers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaControllerTest extends Containers {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateCinema() {
        CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name", new AddressInformation("Blank Street", "Blank City", "00-000"));
        ResponseEntity<CinemaDTO> response = restTemplate.postForEntity("/cinemas", cinemaInformation, CinemaDTO.class);
        CinemaDTO requestBody = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(requestBody);
        assertEquals("Cinema Name", requestBody.name());
        assertNotNull(requestBody.id());
    }

}
