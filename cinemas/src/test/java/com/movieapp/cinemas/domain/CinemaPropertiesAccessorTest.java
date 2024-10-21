package com.movieapp.cinemas.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CinemaPropertiesAccessorTest {
    private final int SEATS_PER_ROW = 20;

    @Test
    void shouldReadSeatsPerRowValue() {
        CinemaPropertiesAccessor cinemaPropertiesAccessor = new CinemaPropertiesAccessor();
        int seatsPerRow = cinemaPropertiesAccessor.getSeatPerRowFromProperties();
        assertEquals(SEATS_PER_ROW, seatsPerRow);
    }

}
