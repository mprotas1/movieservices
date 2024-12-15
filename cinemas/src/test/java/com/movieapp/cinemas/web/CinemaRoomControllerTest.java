package com.movieapp.cinemas.web;

import com.movieapp.cinemas.domain.entity.*;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import com.movieapp.cinemas.service.CinemaRoomService;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import com.movieapp.cinemas.testcontainers.Containers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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

        contextCinema = cinemaRepository.save(new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000", CountryCode.PL), new Coordinates(0.0, 0.0)));
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

    @Test
    @DisplayName("When deleting cinema room then return 204 NO CONTENT status code")
    void shouldDeleteByRoomNumberAndReturn204StatusCode() {
        UUID cinemaId = contextCinema.getIdValue();
        int capacity = 100;
        CinemaRoomDTO cinemaRoomDTO = cinemaRoomService.addRoom(new CinemaRoomInformation(contextCinema.getId().getUuid(), capacity));

        restTemplate.delete(ROOMS_PATH + "/{roomNumber}",
                cinemaId.toString(),
                cinemaRoomDTO.number());

        assertFalse(cinemaRoomRepository.findByCinemaAndNumber(contextCinema.getId(), cinemaRoomDTO.number()).isPresent());
    }

    @Test
    @DisplayName("When updating cinema room capacity then return 200 OK status code and updated DTO. Capacity and seats list should be updated")
    void shouldUpdateRoomCapacity() {
        UUID cinemaId = contextCinema.getIdValue();
        int capacity = 100;
        CinemaRoomDTO cinemaRoomDTO = cinemaRoomService.addRoom(new CinemaRoomInformation(contextCinema.getId().getUuid(), capacity));

        int newCapacity = 200;
        ResponseEntity<CinemaRoomDTO> cinemaRoomDTOResponseEntity = restTemplate.exchange(
                ROOMS_PATH + "/{roomNumber}?newCapacity={newCapacity}",
                HttpMethod.PUT,
                null,
                CinemaRoomDTO.class,
                cinemaId.toString(),
                cinemaRoomDTO.number(),
                newCapacity);

        CinemaRoomDTO updatedCinemaRoomDTO = cinemaRoomDTOResponseEntity.getBody();

        assertNotNull(updatedCinemaRoomDTO);
        assertEquals(HttpStatus.OK, cinemaRoomDTOResponseEntity.getStatusCode());
        assertEquals(newCapacity, updatedCinemaRoomDTO.capacity());

        CinemaRoom room = cinemaRoomRepository.findById(new CinemaRoomId(updatedCinemaRoomDTO.roomId())).get();
        assertNotNull(room);
        assertEquals(newCapacity, room.getCapacity());
        assertEquals(newCapacity, room.getSeats().size());
    }

}
