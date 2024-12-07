package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CountryCode;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.InMemoryCinemaRepository;
import com.movieapp.cinemas.infrastructure.location.CinemaLocationService;
import com.movieapp.cinemas.infrastructure.location.InMemoryCinemaLocationService;
import com.movieapp.cinemas.service.model.AddressInformation;
import com.movieapp.cinemas.service.model.CinemaDTO;
import com.movieapp.cinemas.service.model.CinemaInformation;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CinemaInMemoryServiceTest {
    private CinemaService cinemaService;
    private CinemaRepository cinemaRepository;
    private CinemaLocationService locationService;

    private CinemaInformation information;

    @BeforeEach
    void setUp() {
        cinemaRepository = new InMemoryCinemaRepository();
        locationService = new InMemoryCinemaLocationService();
        cinemaService = new TheatreService(locationService, cinemaRepository);

        AddressInformation cinemaAddressInformation = new AddressInformation("City", "Street", "00-000", CountryCode.PL);
        information = new CinemaInformation("CinemaName", cinemaAddressInformation);

        cinemaRepository.deleteAll();
    }

    @Test
    void shouldCreateCinemaWithValidData() {
        CinemaDTO cinema = cinemaService.createCinema(information);

        assertNotNull(cinema);
        assertEquals(information.name(), cinema.name());
    }

    @ParameterizedTest
    @CsvSource({" , Street, PostalCode", "City, , 00-000", "City, Street, "})
    void shouldRejectCinemaWithInvalidAddress(String city, String street, String postalCode) {
        AddressInformation cinemaAddressInformation = new AddressInformation(city, street, postalCode, CountryCode.PL);
        CinemaInformation cinema = new CinemaInformation("CinemaName", cinemaAddressInformation);
        assertThrows(IllegalArgumentException.class, () -> cinemaService.createCinema(cinema));
    }

    @Test
    void shouldNotCreateCinemaWithInvalidData() {
        CinemaInformation information = new CinemaInformation(null, new AddressInformation("City", "Street", "00-000", CountryCode.PL));
        assertThrows(IllegalArgumentException.class, () -> cinemaService.createCinema(information));
    }

    @Test
    void shouldNotCreateCinemaWithExistingName() {
        CinemaDTO createdCinema = cinemaService.createCinema(information);
        assertThrows(EntityExistsException.class, () -> cinemaService.createCinema(information));
    }

    @Test
    void shouldFindCinemaByName() {
        CinemaDTO createdCinema = cinemaService.createCinema(information);
        CinemaDTO foundCinema = cinemaService.findByName(information.name());
        assertEquals(createdCinema, foundCinema);
    }

    @Test
    void shouldDeleteCinemaById() {
        CinemaDTO createdCinema = cinemaService.createCinema(information);
        cinemaService.deleteById(new CinemaId(createdCinema.id()));
        assertThrows(EntityNotFoundException.class, () -> cinemaService.findByName(information.name()));
    }

    @Test
    void shouldFindCinemaById() {
        CinemaDTO createdCinema = cinemaService.createCinema(information);
        CinemaDTO foundCinema = cinemaService.findById(new CinemaId(createdCinema.id()));
        assertEquals(createdCinema, foundCinema);
    }

    @Test
    void shouldNotFindCinemaById() {
        assertThrows(EntityNotFoundException.class, () -> cinemaService.findById(new CinemaId(UUID.randomUUID())));
    }

    @Test
    void shouldFindAllCinemas() {
        CinemaDTO createdCinema = cinemaService.createCinema(information);
        assertEquals(1, cinemaService.findAll().size());
    }

    @Test
    void shouldFindAllCinemasPaged() {
        CinemaDTO createdCinema = cinemaService.createCinema(information);
        assertEquals(1, cinemaService.findAll(0, 1).size());
    }

}

