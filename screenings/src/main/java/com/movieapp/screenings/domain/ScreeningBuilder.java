package com.movieapp.screenings.domain;

import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.model.ScreeningTime;

import java.time.Instant;
import java.util.UUID;

public class ScreeningBuilder {
    private MovieId movieId;
    private ScreeningRoomId screeningRoomId;
    private Instant startTime;
    private int duration;

    public ScreeningBuilder withMovieId(UUID movieId) {
        this.movieId = new MovieId(movieId);
        return this;
    }

    public ScreeningBuilder withScreeningRoomId(UUID screeningRoomId) {
        this.screeningRoomId = new ScreeningRoomId(screeningRoomId);
        return this;
    }

    public ScreeningBuilder withStartTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public ScreeningBuilder withDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public Screening build() {
        ScreeningTime time = ScreeningTime.from(startTime, duration);
        return new Screening(movieId, screeningRoomId, time);
    }
}
