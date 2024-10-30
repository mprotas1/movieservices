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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CinemaRoomInMemoryServiceTest {
    private final CinemaRoomService cinemaRoomService;
    private final CinemaRoomRepository cinemaRoomRepository;
    private final CinemaRepository cinemaRepository;

    private Cinema parentCinema;

    CinemaRoomInMemoryServiceTest() {
        this.cinemaRepository = new InMemoryCinemaRepository();
        this.cinemaRoomRepository = new InMemoryCinemaRoomRepository();
        this.cinemaRoomService = new ScreeningRoomService(cinemaRepository, cinemaRoomRepository);
    }

    @BeforeEach
    void setUp() {
        parentCinema = cinemaRepository.save(new Cinema("CinemaName", new Address("City", "Street", "00-000", CountryCode.PL), new Coordinates(0.0, 0.0)));
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

    @Test
    void shouldNotFindRoomById() {
        assertThrows(EntityNotFoundException.class, () -> cinemaRoomService.findById(new CinemaRoomId()));
    }

    @Test
    void shouldDeleteRoomByNumber() {
        int roomCapacity = 100;
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(parentCinema.getIdValue(), roomCapacity);
        CinemaRoomDTO room = cinemaRoomService.addRoom(roomInformation);

        cinemaRoomService.deleteByNumber(parentCinema.getId(), room.number());
        assertThrows(EntityNotFoundException.class, () -> cinemaRoomService.findById(new CinemaRoomId(room.roomId())));
    }

    @ParameterizedTest
    @ValueSource(ints = {155, 200, 300, 101, 100})
    void shouldUpdateRoomCapacityWithHigherValue(int newCapacity) {
        int roomCapacity = 100;
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(parentCinema.getIdValue(), roomCapacity);
        CinemaRoomDTO room = cinemaRoomService.addRoom(roomInformation);

        Optional<CinemaRoom> byId = cinemaRoomRepository.findById(new CinemaRoomId(room.roomId()));

        CinemaRoomDTO updatedRoom = cinemaRoomService.updateCapacity(byId.get().getId(), newCapacity);
        assertEquals(newCapacity, updatedRoom.capacity());
        assertEquals(newCapacity, byId.get().getSeats().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {99, 50, 10, 1})
    void shouldUpdateRoomCapacityWithLowerValue(int newCapacity) {
        int roomCapacity = 100;
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(parentCinema.getIdValue(), roomCapacity);
        CinemaRoomDTO room = cinemaRoomService.addRoom(roomInformation);

        Optional<CinemaRoom> byId = cinemaRoomRepository.findById(new CinemaRoomId(room.roomId()));

        CinemaRoom cinemaRoom = byId.get();
        CinemaRoomDTO updatedRoom = cinemaRoomService.updateCapacity(cinemaRoom.getId(), newCapacity);
        assertEquals(newCapacity, updatedRoom.capacity());
        assertEquals(newCapacity, cinemaRoom.getSeats().size());
    }

}
