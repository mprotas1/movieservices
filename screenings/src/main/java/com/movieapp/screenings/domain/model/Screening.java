package com.movieapp.screenings.domain.model;

import com.movieapp.screenings.domain.builder.ScreeningBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Screening {
    private ScreeningId screeningId;
    private MovieId movieId;
    private ScreeningRoomId screeningRoomId;
    private ScreeningTime time;

    public Screening(MovieId movieId, ScreeningRoomId screeningRoomId, ScreeningTime time) {
        this.screeningId = new ScreeningId(UUID.randomUUID());
        this.movieId = movieId;
        this.screeningRoomId = screeningRoomId;
        this.time = time;
    }

    public static ScreeningBuilder builder() {
        return new ScreeningBuilder();
    }

}
