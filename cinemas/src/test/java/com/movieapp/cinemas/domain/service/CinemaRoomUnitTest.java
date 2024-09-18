package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.model.CinemaRoomDTO;
import com.movieapp.cinemas.domain.model.CinemaRoomInformation;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaRoomUnitTest {
    @InjectMocks
    private ScreeningRoomService screeningRoomService;
    @Mock
    private CinemaRoomRepository cinemaRoomRepository;
    @Mock
    private CinemaRepository cinemaRepository;

    private final Cinema cinema = Cinema.create("Movies Empire",
                                                        Address.create(new AddressInformation("Some street", "Some city", "123-456"))
    );
    private final CinemaRoomInformation defaultOneHundredCapacityRoom = new CinemaRoomInformation(1L, 100);

    @BeforeEach
    void setUp() {
        cinema.setId(1L);
    }

    @Test
    void shouldCreateRoomInCinema() {
        when(cinemaRepository.findById(defaultOneHundredCapacityRoom.cinemaId())).thenReturn(Optional.of(cinema));
        when(cinemaRoomRepository.save(any(CinemaRoom.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CinemaRoomDTO result = screeningRoomService.addRoom(defaultOneHundredCapacityRoom);

        assertEquals(cinema.getId(), result.cinemaId());
        assertEquals(1, result.number());
        assertEquals(100, result.capacity());

        verify(cinemaRepository, times(1)).findById(defaultOneHundredCapacityRoom.cinemaId());
        verify(cinemaRoomRepository, times(1)).save(any(CinemaRoom.class));
    }

    @Test
    void shouldCreateSequentialRoomsInCinema() {
        CinemaRoom existingRoom = CinemaRoom.roomInCinema(cinema, 100, 1);
        cinema.addRoom(existingRoom);
        when(cinemaRepository.findById(anyLong())).thenReturn(Optional.of(cinema));
        when(cinemaRoomRepository.save(any(CinemaRoom.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CinemaRoomDTO result = screeningRoomService.addRoom(defaultOneHundredCapacityRoom);

        assertEquals(cinema.getId(), result.cinemaId());
        assertEquals(2, result.number());
        assertEquals(100, result.capacity());

        verify(cinemaRepository, times(1)).findById(defaultOneHundredCapacityRoom.cinemaId());
        verify(cinemaRoomRepository, times(1)).save(any(CinemaRoom.class));
    }

    @Test
    void shouldCreateRoomWithOneMissingSequenceGap() {
        CinemaRoom room1 = CinemaRoom.roomInCinema(cinema, 100, 1);
        CinemaRoom room3 = CinemaRoom.roomInCinema(cinema, 100, 3);
        cinema.addRoom(room1);
        cinema.addRoom(room3);
        when(cinemaRepository.findById(anyLong())).thenReturn(Optional.of(cinema));
        when(cinemaRoomRepository.save(any(CinemaRoom.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CinemaRoomDTO result = screeningRoomService.addRoom(defaultOneHundredCapacityRoom);

        assertEquals(cinema.getId(), result.cinemaId());
        assertEquals(2, result.number());
        assertEquals(100, result.capacity());

        verify(cinemaRepository, times(1)).findById(defaultOneHundredCapacityRoom.cinemaId());
        verify(cinemaRoomRepository, times(1)).save(any(CinemaRoom.class));
    }

    @Test
    void shouldCreateRoomWithTwoMissingSequencesGap() {
        CinemaRoom room1 = CinemaRoom.roomInCinema(cinema, 100, 1);
        CinemaRoom room4 = CinemaRoom.roomInCinema(cinema, 100, 4);
        cinema.addRoom(room1);
        cinema.addRoom(room4);
        when(cinemaRepository.findById(anyLong())).thenReturn(Optional.of(cinema));
        when(cinemaRoomRepository.save(any(CinemaRoom.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CinemaRoomDTO result = screeningRoomService.addRoom(defaultOneHundredCapacityRoom);

        assertEquals(cinema.getId(), result.cinemaId());
        assertEquals(2, result.number());
        assertEquals(100, result.capacity());

        verify(cinemaRepository, times(1)).findById(defaultOneHundredCapacityRoom.cinemaId());
        verify(cinemaRoomRepository, times(1)).save(any(CinemaRoom.class));
    }

    @Test
    void shouldNotCreateRoomInNonExistingCinema() {
        when(cinemaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> screeningRoomService.addRoom(defaultOneHundredCapacityRoom));
        verify(cinemaRepository, times(1)).findById(defaultOneHundredCapacityRoom.cinemaId());
    }

    @Test
    void shouldUpdateCapacityOfRoom() {
        CinemaRoom room = CinemaRoom.roomInCinema(cinema, 100, 1);
        cinema.addRoom(room);
        when(cinemaRoomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        when(cinemaRoomRepository.save(any(CinemaRoom.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CinemaRoomDTO updatedRoom = screeningRoomService.updateCapacity(room.getId(), 200);

        assertEquals(200, updatedRoom.capacity());
        verify(cinemaRoomRepository, times(1)).save(room);
    }

}
