package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.*;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import com.movieapp.cinemas.domain.repository.InMemoryCinemaRepository;
import com.movieapp.cinemas.domain.repository.InMemoryCinemaRoomRepository;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CinemaRoomInMemoryServiceTest {
    private CinemaRoomService cinemaRoomService;
    private CinemaService cinemaService;
    CinemaRoomRepository cinemaRoomRepository;
    CinemaRepository cinemaRepository;

    private Cinema parentCinema;

    @BeforeEach
    void setUp() {
        cinemaRoomRepository = new InMemoryCinemaRoomRepository();
        cinemaRepository = new InMemoryCinemaRepository();

        cinemaService = new TheatreService(cinemaRepository);
        cinemaRoomService = new ScreeningRoomService(cinemaRepository, cinemaRoomRepository);

        parentCinema = cinemaRepository.save(new Cinema("CinemaName", new Address("City", "Street", "PostalCode", CountryCode.PL)));
        cinemaRoomRepository.deleteAll();
    }

    @Test
    void shouldAddRoomToCinema() {
        int roomCapacity = 100;
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(parentCinema.getIdValue(), roomCapacity);
        CinemaRoomDTO result = cinemaRoomService.addRoom(roomInformation);
        assertNotNull(result);
        assertEquals(roomInformation.capacity(), result.capacity());
        assertEquals(roomInformation.cinemaId(), result.cinemaId());
        assertEquals(1, result.number());
    }

    @Test
    void shouldUpdateRoomCapacity() {
        int roomCapacity = 100;
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(parentCinema.getIdValue(), roomCapacity);
        CinemaRoomDTO room = cinemaRoomService.addRoom(roomInformation);

        Optional<CinemaRoom> byId = cinemaRoomRepository.findById(new CinemaRoomId(room.roomId()));

        int newCapacity = 200;
        CinemaRoomDTO updatedRoom = cinemaRoomService.updateCapacity(byId.get().getId(), newCapacity);
        assertEquals(newCapacity, updatedRoom.capacity());
    }

    @Test
    void shouldDeleteRoom() {
        int roomCapacity = 100;
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(parentCinema.getIdValue(), roomCapacity);
        CinemaRoomDTO room = cinemaRoomService.addRoom(roomInformation);

        Optional<CinemaRoom> byId = cinemaRoomRepository.findById(new CinemaRoomId(room.roomId()));
        cinemaRoomService.deleteRoom(byId.get().getId());

        assertThrows(EntityNotFoundException.class, () -> cinemaRoomService.findById(byId.get().getId()));
    }

    @Test
    void shouldFindRoomById() {
        int roomCapacity = 100;
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(parentCinema.getIdValue(), roomCapacity);
        CinemaRoomDTO room = cinemaRoomService.addRoom(roomInformation);

        CinemaRoomId cinemaRoomId = new CinemaRoomId(room.roomId());
        CinemaRoomDTO cinemaRoomDTO = assertDoesNotThrow(() -> cinemaRoomService.findById(cinemaRoomId));
        assertNotNull(cinemaRoomDTO);
        assertEquals(room.roomId(), cinemaRoomDTO.roomId());
    }

    @Test
    void shouldFindRoomByCinemaId() {
        int roomCapacity = 100;
        CinemaRoomInformation firstInformation = new CinemaRoomInformation(parentCinema.getIdValue(), roomCapacity);
        cinemaRoomService.addRoom(firstInformation);

        CinemaRoomInformation secondInformation = new CinemaRoomInformation(parentCinema.getIdValue(), roomCapacity);
        cinemaRoomService.addRoom(secondInformation);

        assertEquals(2, cinemaRoomService.findByCinemaId(parentCinema.getId()).size());
    }

}
