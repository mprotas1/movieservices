package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.repository.InMemoryCinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.service.model.AddressInformation;
import com.movieapp.cinemas.service.model.CinemaDTO;
import com.movieapp.cinemas.service.model.CinemaInformation;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CinemaInMemoryServiceTest {
    private CinemaService cinemaService;
    private CinemaRepository cinemaRepository;

    private CinemaInformation information;

    @BeforeEach
    void setUp() {
        cinemaRepository = new InMemoryCinemaRepository();
        cinemaService = new TheatreService(cinemaRepository);

        AddressInformation cinemaAddressInformation = new AddressInformation("City", "Street", "PostalCode");
        information = new CinemaInformation("CinemaName", cinemaAddressInformation);

        cinemaRepository.deleteAll();
    }

    @Test
    void shouldCreateCinemaWithValidData() {
        CinemaDTO cinema = cinemaService.createCinema(information);

        assertNotNull(cinema);
        assertEquals(information.name(), cinema.name());
    }

    @Test
    void shouldNotCreateCinemaWithInvalidData() {
        CinemaInformation information = new CinemaInformation(null, new AddressInformation("City", "Street", "PostalCode"));
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

