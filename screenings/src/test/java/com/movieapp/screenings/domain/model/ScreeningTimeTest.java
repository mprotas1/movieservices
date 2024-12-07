package com.movieapp.screenings.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningTimeTest {
    private Instant startTime;

    @BeforeEach
    void setUp() {
        startTime = Instant.now().plus(5, ChronoUnit.MINUTES);
    }

    @Test
    void shouldCreateScreeningTimeFromDuration() {
        ScreeningTime screeningTime = ScreeningTime.from(startTime, 120);

        int twoHours = 120 * 60;
        Instant expectedEndTime = startTime.plusSeconds(twoHours);
        assertEquals(startTime, screeningTime.getStartTime());
        assertEquals(expectedEndTime, screeningTime.getEndTime());
    }

    @Test
    void shouldNotCreateScreeningTimeWithZeroDuration() {
        assertThrows(IllegalArgumentException.class, () -> ScreeningTime.from(startTime, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = { -10, -1, -100, -1000, -666 })
    void shouldNotCreateScreeningTimeWithNegativeDuration(int duration) {
        assertThrows(IllegalArgumentException.class, () -> ScreeningTime.from(startTime, duration));
    }

    @Test
    void shouldReturnTrueWhenScreeningTimesOverlap() {
        ScreeningTime firstTime = ScreeningTime.from(startTime, 120);
        ScreeningTime secondTime = ScreeningTime.from(startTime.plusSeconds(60 * 60), 120);

        assertTrue(firstTime.overlaps(secondTime));
    }

    @Test
    void shouldReturnFalseWhenScreeningTimesDoNotOverlap() {
        ScreeningTime firstTime = ScreeningTime.from(startTime, 120);
        ScreeningTime secondTime = ScreeningTime.from(startTime.plusSeconds(120 * 60), 120);

        assertFalse(firstTime.overlaps(secondTime));
    }

}