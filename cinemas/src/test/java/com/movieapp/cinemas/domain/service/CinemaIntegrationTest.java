package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import com.movieapp.cinemas.testcontainers.Containers;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
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
        assertThrows(EntityExistsException.class, () -> cinemaService.createCinema(cinemaInformation));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCinemaInformation")
    void shouldNotCreateCinemaWithAnyBlankField(CinemaInformation cinemaInformation) {
        assertThrows(ConstraintViolationException.class, () -> cinemaService.createCinema(cinemaInformation));
    }

    private static Stream<Arguments> provideInvalidCinemaInformation() {
        return Stream.of(
                Arguments.of(new CinemaInformation("Cinema Name 1", new AddressInformation("", "Blank City", "00-000"))),
                Arguments.of(new CinemaInformation("Cinema Name 2", new AddressInformation("", "", ""))),
                Arguments.of(new CinemaInformation("Cinema Name 3", new AddressInformation("Blank Street", "", "00-000"))),
                Arguments.of(new CinemaInformation("Cinema Name 4", new AddressInformation("Blank Street", "Blank City", "")))
        );
    }
}
