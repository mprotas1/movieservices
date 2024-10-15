package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CountryCode;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.service.model.AddressInformation;
import com.movieapp.cinemas.service.model.CinemaDTO;
import com.movieapp.cinemas.service.model.CinemaInformation;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaUnitTest {
    @InjectMocks
    private TheatreService cinemaService;
    @Mock
    private CinemaRepository cinemaRepository;

    private final CinemaInformation exampleCinemaInfo = new CinemaInformation("CinemaName", new AddressInformation("City", "Street", "00-000", CountryCode.PL));
    private final Cinema exampleCinema = new Cinema("CinemaName", new Address("City", "Street", "00-000", CountryCode.PL));

    @Test
    @DisplayName("Should create Cinema with valid data")
    void shouldCreateCinemaWithValidData() {
        when(cinemaRepository.save(any(Cinema.class))).thenReturn(exampleCinema);
        CinemaDTO result = cinemaService.createCinema(exampleCinemaInfo);
        assertNotNull(result);
        assertEquals(exampleCinemaInfo.name(), result.name());
        verify(cinemaRepository, times(1)).save(any(Cinema.class));
    }

    @Test
    @DisplayName("Should not create Cinema with null name")
    void shouldNotCreateCinemaWithInvalidData() {
        CinemaInformation cinemaInfo = new CinemaInformation(null, new AddressInformation("City", "Street", "00-000", CountryCode.PL));
        assertThrows(IllegalArgumentException.class, () -> cinemaService.createCinema(cinemaInfo));
        verify(cinemaRepository, never()).save(any(Cinema.class));
    }

    @Test
    @DisplayName("Should not create Cinema with existing name")
    void shouldNotCreateCinemaWithExistingName() {
        when(cinemaRepository.findByName(exampleCinemaInfo.name())).thenReturn(Optional.of(exampleCinema));
        assertThrows(EntityExistsException.class, () -> cinemaService.createCinema(exampleCinemaInfo));
        verify(cinemaRepository, never()).save(any(Cinema.class));
        verify(cinemaRepository, times(1)).findByName(exampleCinema.getName());
    }

    @Test
    @DisplayName("Should find Cinema by id")
    void shouldFindCinemaById() {
        when(cinemaRepository.findById(any())).thenReturn(Optional.of(exampleCinema));
        CinemaDTO result = cinemaService.findById(exampleCinema.getId());
        assertNotNull(result);
        assertEquals(exampleCinema.getName(), result.name());
        verify(cinemaRepository).findById(any());
    }

    @Test
    @DisplayName("Should not find Cinema by id")
    void shouldNotFindCinemaById() {
        when(cinemaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cinemaService.findById(exampleCinema.getId()));
        verify(cinemaRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should find Cinema by name")
    void shouldFindCinemaByName() {
        when(cinemaRepository.findByName("CinemaName")).thenReturn(Optional.of(exampleCinema));
        CinemaDTO result = cinemaService.findByName("CinemaName");
        assertNotNull(result);
        assertEquals(exampleCinema.getName(), result.name());
        verify(cinemaRepository, times(1)).findByName("CinemaName");
    }

    @Test
    @DisplayName("Should not find Cinema by name")
    void shouldNotFindCinemaByName() {
        when(cinemaRepository.findByName("CinemaName")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cinemaService.findByName("CinemaName"));
        verify(cinemaRepository, times(1)).findByName("CinemaName");
    }

    @Test
    @DisplayName("Should delete Cinema by id")
    void shouldDeleteCinemaById() {
        when(cinemaRepository.findById(any())).thenReturn(Optional.of(exampleCinema));
        cinemaService.deleteById(exampleCinema.getId());
        verify(cinemaRepository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Should not delete Cinema by id")
    void shouldNotDeleteCinemaById() {
        when(cinemaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cinemaService.deleteById(exampleCinema.getId()));
        verify(cinemaRepository, never()).deleteById(any());
    }

}
