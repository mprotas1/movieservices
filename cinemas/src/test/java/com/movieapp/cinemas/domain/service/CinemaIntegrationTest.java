package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import com.movieapp.cinemas.testcontainers.Containers;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CinemaIntegrationTest extends Containers {
    @Autowired
    private CinemaService cinemaService;

    @Test
    void shouldCreateCinemaWithValidData() {
        CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name",
                                              new AddressInformation("Blank Street", "Blank City", "00-000")
        );

        CinemaDTO cinema = cinemaService.createCinema(cinemaInformation);

        assertNotNull(cinema);
        assertNotNull(cinema.id());
        assertNotNull(cinema.name());
        assertEquals("Cinema Name", cinema.name());;
    }


    @Test
    void shouldNotCreateCinemaWithBlankName() {
        CinemaInformation cinemaInformation = new CinemaInformation("",
                                              new AddressInformation("Blank Street", "Blank City", "00-000")
        );

        assertThrows(ConstraintViolationException.class, () -> cinemaService.createCinema(cinemaInformation));
    }

    @Test
    void shouldNotCreateCinemaWithExistingName() {
        CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name",
                                              new AddressInformation("Blank Street", "Blank City", "00-000")
        );

        cinemaService.createCinema(cinemaInformation);
        assertThrows(EntityExistsException.class, () -> cinemaService.createCinema(cinemaInformation)); }
}
