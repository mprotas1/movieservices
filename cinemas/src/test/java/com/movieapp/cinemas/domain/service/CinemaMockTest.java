package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CinemaMockTest {
    @InjectMocks
    private TheatreService cinemaService;
    @Mock
    private CinemaRepository cinemaRepository;
    @Mock
    private AddressLocationService addressService;
    @Mock
    private Validator validator;

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
        assertFalse(cinema.id().toString().isEmpty());
        assertFalse(cinema.name().isEmpty());
    }

    @Test
    void shouldRejectCreateCinemaWithBlankName() {
        String name = "";
        CinemaInformation invalidCinema = new CinemaInformation(name, exampleAddressInformation);

        when(addressService.save(any())).thenReturn(exampleAddress);
        when(cinemaRepository.save(any())).thenReturn(exampleCinema);
        doThrow(new ConstraintViolationException(Collections.emptySet())).when(validator).validate(invalidCinema);

        assertThrows(ConstraintViolationException.class, () -> cinemaService.createCinema(invalidCinema));
    }

}
