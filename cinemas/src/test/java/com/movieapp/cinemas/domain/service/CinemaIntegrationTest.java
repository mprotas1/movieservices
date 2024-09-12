package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import com.movieapp.cinemas.testcontainers.Containers;
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
        // given
        CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name",
                                              new AddressInformation("Blank Street", "Blank City", "00-000")
        );

        // when
        CinemaDTO cinema = cinemaService.createCinema(cinemaInformation);

        // then
        assertNotNull(cinema);
        assertNotNull(cinema.id());
        assertNotNull(cinema.name());
        assertEquals("Cinema Name", cinema.name());;
    }

}
