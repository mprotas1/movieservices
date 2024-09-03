package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CinemaServiceSpringTest {
    @InjectMocks
    private TheatreService cinemaService;
    @Mock
    private CinemaRepository cinemaRepository;
    @Mock
    private AddressLocationService addressService;

    CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name", new AddressInformation("Al. Wyzwolenia", "Szczecin", "71-210"));
    Address exampleAddress = new Address(new ObjectId("8941AA7A188358360BEFC879"), "Al. Wyzwolenia", "Szczecin", "71-210", null);
    Cinema exampleCinema = Cinema.create(cinemaInformation.name(), exampleAddress);

    @Test
    void shouldCreateCinemaWithValidData() {
        // 1. Create the CinemaInformation object with valid data
        exampleCinema.setId(new ObjectId("8941AA7A188358360BEFC879"));

        // 2. Call the createCinema method of cinemaService with the CinemaInformation object
        when(addressService.save(any())).thenReturn(exampleAddress);
        when(cinemaRepository.save(any())).thenReturn(exampleCinema);

        CinemaDTO cinema = cinemaService.createCinema(cinemaInformation);

        // 3. Verify that the cinemaRepository.save method was called with the Cinema object
        assertNotNull(cinema);
        assertFalse(cinema.id().toString().isEmpty());
        assertFalse(cinema.name().isEmpty());
    }

    @Test
    void shouldRejectCinemaWithInvalidData() {
        // 2. Call the createCinema method of cinemaService with the CinemaInformation object

        // 3. Verify that the cinemaRepository.save method was not called
    }

}
