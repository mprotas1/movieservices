package com.movieapp.cinemas.web;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.service.model.AddressInformation;
import com.movieapp.cinemas.service.model.CinemaDTO;
import com.movieapp.cinemas.service.model.CinemaInformation;
import com.movieapp.cinemas.testcontainers.Containers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaControllerTest extends Containers {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CinemaRepository cinemaRepository;

    @BeforeEach
    void setUp() {
        cinemaRepository.deleteAll();
    }

    @Test
    @DisplayName("When creating cinema then return 201 CREATED status code and DTO")
    void shouldCreateCinema() {
        CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name", new AddressInformation("Blank Street", "Blank City", "00-000"));

        ResponseEntity<CinemaDTO> postCinemaResponseEntity = restTemplate.postForEntity("/", cinemaInformation, CinemaDTO.class);
        CinemaDTO cinemaResponse = postCinemaResponseEntity.getBody();

        assertEquals(HttpStatus.CREATED, postCinemaResponseEntity.getStatusCode());
        assertNotNull(cinemaResponse);
        assertEquals(cinemaInformation.name(), cinemaResponse.name());
        assertNotNull(cinemaResponse.id());
    }

    @Test
    @DisplayName("When finding cinema by id then return 200 OK status code and DTO")
    void shouldFindCinemaById() {
        Cinema cinema = cinemaRepository.save(new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000")));
        CinemaId cinemaId = cinema.getId();

        ResponseEntity<CinemaDTO> getCinemaResponseEntity = restTemplate.getForEntity("/{id}", CinemaDTO.class, cinemaId.getUuid().toString());
        CinemaDTO foundCinemaDTO = getCinemaResponseEntity.getBody();

        assertEquals(HttpStatus.OK, getCinemaResponseEntity.getStatusCode());
        assertNotNull(foundCinemaDTO);
        assertEquals(cinemaId.getUuid(), foundCinemaDTO.id());
        assertEquals(cinema.getName(), foundCinemaDTO.name());
    }

    @Test
    @DisplayName("When finding all cinemas then return 200 OK status code and all DTOs")
    void shouldFindAllCinemas() {
        Cinema firstCinema = cinemaRepository.save(new Cinema("Cinema Name 1", new Address("Blank Street 1", "Blank City 1", "00-001")));
        Cinema secondCinema = cinemaRepository.save(new Cinema("Cinema Name 2", new Address("Blank Street 2", "Blank City 2", "00-002")));

        ResponseEntity<CinemaDTO[]> getCinemasResponseEntity = restTemplate.getForEntity("/", CinemaDTO[].class);
        CinemaDTO[] foundCinemasDTO = getCinemasResponseEntity.getBody();

        assertEquals(HttpStatus.OK, getCinemasResponseEntity.getStatusCode());
        assertNotNull(foundCinemasDTO);
        assertEquals(2, foundCinemasDTO.length);
    }

    @Test
    @DisplayName("When finding cinema by name then return 200 OK status code and DTO")
    void shouldFindCinemaByName() {
        Cinema cinema = cinemaRepository.save(new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000")));

        ResponseEntity<CinemaDTO> getCinemaResponseEntity = restTemplate.getForEntity("/search?name={name}", CinemaDTO.class, cinema.getName());
        CinemaDTO foundCinemaDTO = getCinemaResponseEntity.getBody();

        assertEquals(HttpStatus.OK, getCinemaResponseEntity.getStatusCode());
        assertNotNull(foundCinemaDTO);
        assertEquals(cinema.getName(), foundCinemaDTO.name());
    }

    @Test
    @DisplayName("When deleting cinema then return 204 NO CONTENT status code")
    void shouldDeleteCinemaById() {
        Cinema cinema = cinemaRepository.save(new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000")));
        CinemaId cinemaId = cinema.getId();

        assertTrue(cinemaRepository.findById(cinemaId).isPresent());

        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/{id}", HttpMethod.DELETE, null, Void.class, cinemaId.getUuid().toString());

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
        assertEquals(0, cinemaRepository.findAll().size());
    }

    @Test
    @DisplayName("When creating cinema with invalid Cinema's name - return 400 BAD REQUEST and ProblemDetail")
    void shouldReturnBadRequestWhenCreatingCinemaWithInvalidName() {
        CinemaInformation cinemaInformation = new CinemaInformation("", new AddressInformation("Blank Street", "Blank City", "00-000"));

        ResponseEntity<ProblemDetail> postCinemaResponseEntity = restTemplate.postForEntity("/", cinemaInformation, ProblemDetail.class);
        ProblemDetail problemDetail = postCinemaResponseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, postCinemaResponseEntity.getStatusCode());
        assertNotNull(problemDetail);
    }

    @Test
    @DisplayName("When creating cinema with existing Cinema's name - return 400 BAD REQUEST and ProblemDetail")
    void shouldReturnBadRequestWhenCreatingCinemaWithExistingName() {
        Cinema cinema = cinemaRepository.save(new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000")));
        CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name", new AddressInformation("Blank Street", "Blank City", "00-000"));

        ResponseEntity<ProblemDetail> postCinemaResponseEntity = restTemplate.postForEntity("/", cinemaInformation, ProblemDetail.class);
        ProblemDetail problemDetail = postCinemaResponseEntity.getBody();

        assertEquals(HttpStatus.CONFLICT, postCinemaResponseEntity.getStatusCode());
        assertNotNull(problemDetail);
        assertEquals(String.format("Cinema with name: %s already exists", cinema.getName()), problemDetail.getDetail());
        assertEquals(409, problemDetail.getStatus());
    }

}
