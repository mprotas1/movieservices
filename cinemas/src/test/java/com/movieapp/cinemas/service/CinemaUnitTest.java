package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.service.model.AddressInformation;
import com.movieapp.cinemas.service.model.CinemaDTO;
import com.movieapp.cinemas.service.model.CinemaInformation;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CinemaUnitTest {
    @InjectMocks
    private TheatreService cinemaService;
    @Mock
    private CinemaRepository cinemaRepository;

    private final Cinema exampleCinema = new Cinema("CinemaName", new Address("City", "Street", "PostalCode"));

    @Test
    @DisplayName("Should create Cinema with valid data")
    void shouldCreateCinemaWithValidData() {
        CinemaInformation cinemaInfo = new CinemaInformation("CinemaName", new AddressInformation("City", "Street", "PostalCode"));
        when(cinemaRepository.save(any(Cinema.class))).thenReturn(exampleCinema);
        CinemaDTO result = cinemaService.createCinema(cinemaInfo);
        assertNotNull(result);
        assertEquals("CinemaName", result.name());
        verify(cinemaRepository).save(any(Cinema.class));
    }

    @Test
    @DisplayName("Should not create Cinema with null name")
    void shouldNotCreateCinemaWithInvalidData() {
        CinemaInformation cinemaInfo = new CinemaInformation(null, new AddressInformation("City", "Street", "PostalCode"));
        assertThrows(IllegalArgumentException.class, () -> cinemaService.createCinema(cinemaInfo));
    }

    @Test
    @DisplayName("Should not create Cinema with existing name")
    void shouldNotCreateCinemaWithExistingName() {
        CinemaInformation cinemaInfo = new CinemaInformation("CinemaName", new AddressInformation("City", "Street", "PostalCode"));
        when(cinemaRepository.findByName("CinemaName")).thenReturn(Optional.of(exampleCinema));
        assertThrows(EntityExistsException.class, () -> cinemaService.createCinema(cinemaInfo));
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

}
