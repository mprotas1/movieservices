package com.movieapp.screenings.domain.builder;

import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.model.ScreeningTime;

import java.time.Instant;
import java.util.UUID;

public class ScreeningBuilder {
    private MovieId movieId;
    private ScreeningRoomId screeningRoomId;
    private ScreeningTime time;

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

    public Screening build() {
        return new Screening(movieId, screeningRoomId, time);
    }
}
