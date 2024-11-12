package com.movieapp.screenings.interfaces.rest;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.containers.TestContainers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScreeningControllerTest extends TestContainers {
    private final String BASE_URL = "/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("When creating screening then return 201 CREATED status code and DTO")
    void shouldCreateScreening() {
        ScreeningCreateRequest request = new ScreeningCreateRequest(UUID.randomUUID(), 120, UUID.randomUUID(), Instant.now().plusSeconds(5));
        ResponseEntity<ScreeningDTO> responseEntity = restTemplate.postForEntity(BASE_URL, request, ScreeningDTO.class);
        ScreeningDTO dto = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(dto);
        assertEquals(request.movieId(), dto.movieId());
        assertEquals(responseEntity.getHeaders().getLocation().getPath(), "/screenings/" + dto.screeningId());
    }

}
