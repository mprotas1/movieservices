package com.movieapp.cinemas.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CoordinatesTest {

    @Test
    void coordinatesWithinValidRange() {
        Coordinates coordinates = new Coordinates(45.0, 90.0);
        Assertions.assertEquals(45.0, coordinates.latitude());
        Assertions.assertEquals(90.0, coordinates.longitude());
    }

    @Test
    void latitudeBelowMinimumThrowsException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Coordinates(-91.0, 0.0);
        });
        Assertions.assertEquals("Latitude must be between -90 and 90", exception.getMessage());
    }

    @Test
    void latitudeAboveMaximumThrowsException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Coordinates(91.0, 0.0);
        });
        Assertions.assertEquals("Latitude must be between -90 and 90", exception.getMessage());
    }

    @Test
    void longitudeBelowMinimumThrowsException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Coordinates(0.0, -181.0);
        });
        Assertions.assertEquals("Longitude must be between -180 and 180", exception.getMessage());
    }

    @Test
    void longitudeAboveMaximumThrowsException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Coordinates(0.0, 181.0);
        });
        Assertions.assertEquals("Longitude must be between -180 and 180", exception.getMessage());
    }

    @Test
    void latitudeAtMinimumBoundary() {
        Coordinates coordinates = new Coordinates(-90.0, 0.0);
        Assertions.assertEquals(-90.0, coordinates.latitude());
    }

    @Test
    void latitudeAtMaximumBoundary() {
        Coordinates coordinates = new Coordinates(90.0, 0.0);
        Assertions.assertEquals(90.0, coordinates.latitude());
    }

    @Test
    void longitudeAtMinimumBoundary() {
        Coordinates coordinates = new Coordinates(0.0, -180.0);
        Assertions.assertEquals(-180.0, coordinates.longitude());
    }

    @Test
    void longitudeAtMaximumBoundary() {
        Coordinates coordinates = new Coordinates(0.0, 180.0);
        Assertions.assertEquals(180.0, coordinates.longitude());
    }
}