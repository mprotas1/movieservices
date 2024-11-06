package com.movieapp.screenings.domain.model;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.util.Assert.isTrue;

public class ScreeningTime {
    private final Instant startTime;
    private final Instant endTime;

    private ScreeningTime(Instant startTime, int durationInMinutes) {
        isTrue(durationInMinutes > 0, "Duration must be non-negative");
        isTrue(startTimeIsInFuture(startTime), "Start time must be in the future");
        this.startTime = startTime;
        this.endTime = calculateEndTime(startTime, Duration.of(durationInMinutes, ChronoUnit.MINUTES));
    }

    public static ScreeningTime from(Instant startTime, int durationInMinutes) {
        return new ScreeningTime(startTime, durationInMinutes);
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public boolean overlaps(ScreeningTime other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    private boolean startTimeIsInFuture(Instant startTime) {
        return startTime.isAfter(Instant.now());
    }

    private Instant calculateEndTime(Instant startTime, Duration duration) {
        return startTime.plus(duration);
    }

}
