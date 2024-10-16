package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.*;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import com.movieapp.cinemas.domain.repository.InMemoryCinemaRepository;
import com.movieapp.cinemas.domain.repository.InMemoryCinemaRoomRepository;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatInMemoryServiceTest {
    private CinemaRoomService cinemaRoomService;
    private CinemaRepository cinemaRepository;
    private CinemaRoomRepository cinemaRoomRepository;

    private Cinema baseCinema = new Cinema("CinemaName", new Address("City", "Street", "00-000", CountryCode.PL));

    @BeforeEach
    void setUp() {
        cinemaRepository = new InMemoryCinemaRepository();
        cinemaRoomRepository = new InMemoryCinemaRoomRepository();
        cinemaRoomService = new ScreeningRoomService(cinemaRepository, cinemaRoomRepository);

        baseCinema = cinemaRepository.save(baseCinema);
    }

    @AfterEach
    void cleanUp() {
        cinemaRepository.deleteAll();
    }

    @Test
    void shouldCreateSeatsWhenCreatingCinemaRoom() {
        int capacity = 30;
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(baseCinema.getId().getUuid(), capacity);

        CinemaRoomDTO room = cinemaRoomService.addRoom(roomInformation);
        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(new CinemaRoomId(room.roomId())).get();

        assertEquals(capacity, cinemaRoom.getSeats().size());
    }

}
