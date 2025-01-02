package com.movieapp.screenings.domain.builder;

import com.movieapp.screenings.domain.model.*;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class ScreeningBuilder {
    private MovieId movieId;
    private ScreeningRoomId screeningRoomId;
    private ScreeningTime time;
    private String title;
    private int screeningRoomNumber;
    private ScreeningSeats seats;

    public ScreeningBuilder withMovieId(Long movieId) {
        this.movieId = new MovieId(movieId);
        return this;
    }

    public ScreeningBuilder withScreeningRoomId(UUID screeningRoomId) {
        this.screeningRoomId = new ScreeningRoomId(screeningRoomId);
        return this;
    }

    public ScreeningBuilder withScreeningTime(Instant startTime, int duration) {
        this.time = ScreeningTime.from(startTime, duration);
        return this;
    }

    public ScreeningBuilder withMovieTitle(String movieTitle) {
        this.title = movieTitle;
        return this;
    }

    public ScreeningBuilder withScreeningRoomNumber(int screeningRoomNumber) {
        this.screeningRoomNumber = screeningRoomNumber;
        return this;
    }

    public ScreeningBuilder withScreeningSeats(Collection<ScreeningSeat> screeningSeats) {
        this.seats = new ScreeningSeats(new HashSet<>(screeningSeats));
        return this;
    }

    public Screening build() {
        return new Screening(
                movieId,
                screeningRoomId,
                time,
                title,
                screeningRoomNumber,
                seats
        );
    }
}
