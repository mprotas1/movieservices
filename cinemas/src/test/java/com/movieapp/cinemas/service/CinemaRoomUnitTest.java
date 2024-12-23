package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.*;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaRoomUnitTest {
    @InjectMocks
    private ScreeningRoomService roomService;
    @Mock
    private CinemaRoomRepository roomRepository;
    @Mock
    private CinemaRepository cinemaRepository;

    private final Cinema exampleCinema = new Cinema("CinemaName", new Address("City", "Street", "00-000", CountryCode.PL), new Coordinates(0.0, 0.0));

    @Test
    @DisplayName("Should create CinemaRoom with valid data")
    void shouldCreateRoomWithValidData() {
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(exampleCinema.getIdValue(), 100);
        when(cinemaRepository.findById(any(CinemaId.class))).thenReturn(Optional.of(exampleCinema));
        int nextRoomNumber = exampleCinema.getNextRoomNumber();
        CinemaRoom room = new CinemaRoom(nextRoomNumber, roomInformation.capacity(), exampleCinema);
        when(roomRepository.save(any(CinemaRoom.class))).thenReturn(room);
        CinemaRoomDTO result = roomService.addRoom(roomInformation);

        assertNotNull(result);
        assertEquals(roomInformation.capacity(), result.capacity());
        assertEquals(roomInformation.cinemaId(), result.cinemaId());
        assertEquals(nextRoomNumber, result.number());

        verify(cinemaRepository, times(1)).findById(any(CinemaId.class));
        verify(roomRepository, times(1)).save(any(CinemaRoom.class));
    }

    @Test
    @DisplayName("Should not create CinemaRoom with existing CinemaRoom number")
    void shouldNotCreateRoomWithExistingNumberInCinema() {
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(exampleCinema.getIdValue(), 100);
        int nextRoomNumber = exampleCinema.getNextRoomNumber();
        when(cinemaRepository.findById(any(CinemaId.class))).thenReturn(Optional.of(exampleCinema));
        when(roomRepository.findByCinemaAndNumber(any(CinemaId.class), anyInt())).thenReturn(Optional.of(new CinemaRoom(nextRoomNumber, 100, exampleCinema)));

        assertThrows(EntityExistsException.class, () -> roomService.addRoom(roomInformation));

        verify(cinemaRepository, times(1)).findById(any(CinemaId.class));
        verify(roomRepository, never()).save(any(CinemaRoom.class));
    }

    @Test
    @DisplayName("Should not create CinemaRoom with invalid CinemaId")
    void shouldNotCreateRoomWithInvalidCinemaId() {
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(Mockito.mock(UUID.class), 100);
        when(cinemaRepository.findById(any(CinemaId.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roomService.addRoom(roomInformation));

        verify(cinemaRepository, times(1)).findById(any(CinemaId.class));
        verify(roomRepository, never()).save(any(CinemaRoom.class));
    }

    @Test
    @DisplayName("Should update CinemaRoom capacity")
    void shouldUpdateRoomCapacity() {
        CinemaRoom room = new CinemaRoom(1, 100, exampleCinema);
        when(roomRepository.findByCinemaAndNumber(any(), anyInt())).thenReturn(Optional.of(room));
        int newCapacity = 200;
        CinemaRoom updatedRoom = new CinemaRoom(1, newCapacity, exampleCinema);
        when(roomRepository.save(any(CinemaRoom.class))).thenReturn(updatedRoom);
        CinemaRoomDTO cinemaRoomDTO = roomService.updateCapacity(exampleCinema.getId(), room.getNumber(), newCapacity);

        assertNotNull(cinemaRoomDTO);
        assertEquals(newCapacity, cinemaRoomDTO.capacity());

        verify(roomRepository, times(1)).findByCinemaAndNumber(any(), anyInt());
        verify(roomRepository, times(1)).save(any(CinemaRoom.class));
    }

    @Test
    @DisplayName("Should not update CinemaRoom capacity with negative value")
    void shouldNotUpdateRoomCapacityWithNegativeValue() {
        CinemaRoom room = new CinemaRoom(1, 100, exampleCinema);
        when(roomRepository.findByCinemaAndNumber(any(), anyInt())).thenReturn(Optional.of(room));
        int newCapacity = -1;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> roomService.updateCapacity(exampleCinema.getId(), room.getNumber(), newCapacity));

        assertEquals("Cinema room capacity [-1] must be greater than 0", exception.getMessage());

        verify(roomRepository, times(1)).findByCinemaAndNumber(any(), anyInt());
        verify(roomRepository, never()).save(any(CinemaRoom.class));
    }

    @Test
    @DisplayName("Should not update CinemaRoom capacity with zero value")
    void shouldNotUpdateRoomCapacityWithZeroValue() {
        CinemaRoom room = new CinemaRoom(1, 100, exampleCinema);
        when(roomRepository.findByCinemaAndNumber(any(), anyInt())).thenReturn(Optional.of(room));
        int newCapacity = 0;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> roomService.updateCapacity(exampleCinema.getId(), 1, newCapacity));

        assertEquals("Cinema room capacity [0] must be greater than 0", exception.getMessage());

        verify(roomRepository, times(1)).findByCinemaAndNumber(any(), anyInt());
        verify(roomRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete CinemaRoom by CinemaRoomId")
    void shouldDeleteRoomByCinemaRoomId() {
        CinemaRoom room = new CinemaRoom(1, 100, exampleCinema);
        roomService.deleteRoom(room.getId());

        verify(roomRepository, times(1)).deleteById(any());
    }

}
