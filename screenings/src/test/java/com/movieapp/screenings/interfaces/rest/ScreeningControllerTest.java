package com.movieapp.screenings.interfaces.rest;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.containers.TestContainers;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.respository.ScreeningRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
class ScreeningControllerTest extends TestContainers {
    private final String BASE_URL = "/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ScreeningRepository repository;

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

    @Test
    @DisplayName("When creating screening with past start time then return 400 BAD REQUEST status code and ProblemDetails")
    void shouldNotCreateScreening() {
        ScreeningCreateRequest request = new ScreeningCreateRequest(UUID.randomUUID(), 120, UUID.randomUUID(), Instant.now().plusSeconds(5));
        ResponseEntity<ProblemDetail> responseEntity = restTemplate.postForEntity(BASE_URL, request, ProblemDetail.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    @DisplayName("When finding all screenings then return 200 OK status code and list of DTOs")
    void shouldFindAllScreenings() {
        Screening screening = Screening.builder()
                .withMovieId(UUID.randomUUID())
                .withScreeningTime(Instant.now().plusSeconds(5), 120)
                .withScreeningRoomId(UUID.randomUUID())
                .build();
        repository.save(screening);
        repository.save(screening);

        ResponseEntity<ScreeningDTO[]> responseEntity = restTemplate.getForEntity(BASE_URL, ScreeningDTO[].class);
        ScreeningDTO[] dtos = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dtos);
        assertEquals(2, dtos.length);
    }

}
