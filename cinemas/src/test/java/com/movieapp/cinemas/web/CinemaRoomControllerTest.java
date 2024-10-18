package com.movieapp.cinemas.web;

import com.movieapp.cinemas.domain.entity.*;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import com.movieapp.cinemas.service.CinemaRoomService;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.testcontainers.Containers;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaRoomControllerTest extends Containers {
    private final String ROOMS_PATH = "/{cinemaId}/rooms";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private CinemaRoomRepository cinemaRoomRepository;
    @Autowired
    private CinemaRoomService cinemaRoomService;

    private Cinema contextCinema;

    @BeforeEach
    void setUp() {
        cinemaRepository.deleteAll();
        cinemaRoomRepository.deleteAll();

        contextCinema = cinemaRepository.save(new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000", CountryCode.PL)));
    }

    @Test
    @DisplayName("When creating cinema room then return 201 CREATED status code and DTO")
    void shouldCreateCinemaRoom() {
        UUID cinemaId = contextCinema.getIdValue();
        int capacity = 100;
        ResponseEntity<CinemaRoomDTO> cinemaRoomDTOResponseEntity = restTemplate.postForEntity(ROOMS_PATH + "?capacity={capacity}",
                null,
                CinemaRoomDTO.class,
                cinemaId.toString(),
                capacity);

        CinemaRoomDTO cinemaRoomDTO = cinemaRoomDTOResponseEntity.getBody();

        assertNotNull(cinemaRoomDTO);
        assertEquals(HttpStatus.OK, cinemaRoomDTOResponseEntity.getStatusCode());
        assertEquals(capacity, cinemaRoomDTO.capacity());

        CinemaRoom room = cinemaRoomRepository.findById(new CinemaRoomId(cinemaRoomDTO.roomId())).get();
        assertNotNull(room);
        assertEquals(capacity, room.getCapacity());
        assertEquals(capacity, room.getSeats().size());
    }


}
