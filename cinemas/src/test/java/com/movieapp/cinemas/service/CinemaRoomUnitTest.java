package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CinemaRoomUnitTest {
    @InjectMocks
    private ScreeningRoomService roomService;
    @Mock
    private CinemaRoomRepository roomRepository;
    @Mock
    private CinemaRepository cinemaRepository;

    private final Cinema exampleCinema = new Cinema("CinemaName", new Address("City", "Street", "PostalCode"));

    @Test
    void shouldCreateRoomWithValidData() {
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(exampleCinema.getIdValue(), 100);
        when(cinemaRepository.findById(any(CinemaId.class))).thenReturn(Optional.of(exampleCinema));
        int nextRoomNumber = exampleCinema.getNextRoomNumber();
        CinemaRoom room = new CinemaRoom(nextRoomNumber, roomInformation.capacity(), exampleCinema);
        when(roomRepository.save(any(CinemaRoom.class))).thenReturn(room);
        CinemaRoomDTO cinemaRoomDTO = roomService.addRoom(roomInformation);

        assertNotNull(cinemaRoomDTO);
    }

}
