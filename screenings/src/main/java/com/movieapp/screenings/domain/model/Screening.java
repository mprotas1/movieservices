package com.movieapp.screenings.domain.model;

import com.movieapp.screenings.domain.builder.ScreeningBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Screening {
    private ScreeningId screeningId;
    private MovieId movieId;
    private CinemaId cinemaId;
    private ScreeningRoomId screeningRoomId;
    private ScreeningTime time;
    private String movieTitle;
    private int screeningRoomNumber;
    private ScreeningSeats seats;

    public Screening(MovieId movieId,
                     ScreeningRoomId screeningRoomId,
                     ScreeningTime time,
                     String title,
                     int screeningRoomNumber,
                     ScreeningSeats seats) {
        this.screeningId = new ScreeningId(UUID.randomUUID());
        this.movieId = movieId;
        this.screeningRoomId = screeningRoomId;
        this.time = time;
        this.movieTitle = title;
        this.screeningRoomNumber = screeningRoomNumber;
        this.seats = seats;
    }

    public static ScreeningBuilder builder() {
        return new ScreeningBuilder();
    }

}
