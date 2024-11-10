package com.movieapp.screenings.domain.builder;

import com.movieapp.screenings.domain.model.Screening;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScreeningBuilderTest {

    @Test
    void shouldBuildScreening() {
        UUID movieId = UUID.randomUUID();
        UUID screeningRoomId = UUID.randomUUID();
        Instant startTime = Instant.now().plus(5, ChronoUnit.SECONDS);
        int duration = 120;

        Screening screening = Screening.builder()
                .withMovieId(movieId)
                .withScreeningRoomId(screeningRoomId)
                .withScreeningTime(startTime, duration)
                .build();

        assertNotNull(screening);
        assertEquals(movieId, screening.getMovieId().id());
        assertEquals(screeningRoomId, screening.getScreeningRoomId().id());
        assertEquals(startTime, screening.getTime().getStartTime());
    }

}
