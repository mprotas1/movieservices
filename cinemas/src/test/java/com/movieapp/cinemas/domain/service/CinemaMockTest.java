package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import com.movieapp.cinemas.domain.model.CinemaRoomInformation;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaMockTest {
    @InjectMocks
    private TheatreService cinemaService;
    @Mock
    private CinemaRepository cinemaRepository;
    @Mock
    private AddressLocationService addressService;

    AddressInformation exampleAddressInformation = new AddressInformation("Al. Wyzwolenia", "Szczecin", "71-210");
    CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name", exampleAddressInformation);
    Address exampleAddress = Address.create(exampleAddressInformation);
    Cinema exampleCinema = Cinema.create(cinemaInformation.name(), exampleAddress);

    @Test
    void shouldCreateCinemaWithValidData() {
        exampleCinema.setId(1L);

        when(addressService.save(any())).thenReturn(exampleAddress);
        when(cinemaRepository.save(any())).thenReturn(exampleCinema);

        CinemaDTO cinema = cinemaService.createCinema(cinemaInformation);

        assertNotNull(cinema);
        assertEquals("71-210 Szczecin, Al. Wyzwolenia", cinema.formattedAddress());
        assertFalse(cinema.id().toString().isEmpty());
        assertFalse(cinema.name().isEmpty());
        
        verify(cinemaRepository, times(1)).findByName(cinemaInformation.name());
        verify(addressService, times(1)).save(exampleAddressInformation);
    }

    @Test
    void shouldNotCreateCinemaWithExistingName() {
        exampleCinema.setId(1L);
        when(cinemaRepository.findByName(any())).thenReturn(Optional.of(exampleCinema));
        assertThrows(EntityExistsException.class, () -> cinemaService.createCinema(cinemaInformation));
        verify(cinemaRepository, times(1)).findByName(cinemaInformation.name());
    }

    @Test
    void shouldFindCinemaById() {
        Long id = 1L;
        exampleCinema.setId(id);
        when(cinemaRepository.findById(any())).thenReturn(Optional.of(exampleCinema));
        CinemaDTO cinema = cinemaService.findById(id);
        assertNotNull(cinema);
        assertEquals(id, cinema.id());
        verify(cinemaRepository, times(1)).findById(id);
    }

    @Test
    void shouldNotFindCinemaByNonExistingId() {
        Long id = 1L;
        when(cinemaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cinemaService.findById(id));
        verify(cinemaRepository, times(1)).findById(id);
    }

    @Test
    void shouldFindCinemaByName() {
        String name = "Cinema Name";
        exampleCinema.setId(1L);
        when(cinemaRepository.findByName(any())).thenReturn(Optional.of(exampleCinema));
        CinemaDTO cinema = cinemaService.findByName(name);
        assertNotNull(cinema);
        assertEquals(name, cinema.name());
        verify(cinemaRepository, times(1)).findByName(name);
    }

    @Test
    void shouldNotFindCinemaByNonExistingName() {
        String name = "Cinema Name";
        when(cinemaRepository.findByName(name)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cinemaService.findByName(name));
        verify(cinemaRepository, times(1)).findByName(name);
    }

    @Test
    void shouldDeleteCinemaById() {
        Long id = 1L;
        exampleCinema.setId(id);
        when(cinemaRepository.findById(any())).thenReturn(Optional.of(exampleCinema));
        assertDoesNotThrow(() -> cinemaService.deleteById(id));

        verify(cinemaRepository, times(1)).findById(id);
    }

    @Test
    void shouldNotDeleteCinemaByNonExistingId() {
        Long id = 1L;
        when(cinemaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cinemaService.deleteById(id));

        verify(cinemaRepository, times(1)).findById(id);
    }

    @Test
    void shouldAddRoomToCinema() {
        exampleCinema.setId(1L);
        exampleCinema.setRooms(new ArrayList<>());
        when(cinemaRepository.findById(any())).thenReturn(Optional.of(exampleCinema));
        when(cinemaRepository.save(any())).thenReturn(exampleCinema);
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(exampleCinema.getId(), 25);
        CinemaRoomInformation createdRoomInformation = cinemaService.addRoom(roomInformation);

        assertNotNull(createdRoomInformation);
        assertEquals(1L, createdRoomInformation.cinemaId());
        assertEquals(25, createdRoomInformation.capacity());
        assertFalse(exampleCinema.getRooms().isEmpty());
        assertEquals(1, exampleCinema.getRooms().size());

        verify(cinemaRepository, times(1)).findById(exampleCinema.getId());
        verify(cinemaRepository, times(1)).save(exampleCinema);
    }

    @Test
    void shouldNotAddRoomToNonExistingCinema() {
        CinemaRoomInformation roomInformation = new CinemaRoomInformation(999L, 25);
        when(cinemaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cinemaService.addRoom(roomInformation));
    }



}
