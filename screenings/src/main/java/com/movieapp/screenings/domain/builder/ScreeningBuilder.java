package com.movieapp.screenings.domain.builder;

import com.movieapp.screenings.application.dto.ScreeningSeatDTO;
import com.movieapp.screenings.application.dto.SeatType;
import com.movieapp.screenings.domain.model.*;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public class ScreeningBuilder {
    private MovieId movieId;
    private ScreeningRoomId screeningRoomId;
    private ScreeningTime time;
    private CinemaId cinemaId;
    private String title;
    private int screeningRoomNumber;
    private Collection<ScreeningSeatDTO> seats;

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

    public ScreeningBuilder withScreeningSeats(Collection<ScreeningSeatDTO> screeningSeats) {
        this.seats = screeningSeats;
        return this;
    }

    public ScreeningBuilder withCinemaId(UUID cinemaId) {
        this.cinemaId = new CinemaId(cinemaId);
        return this;
    }

    public ScreeningSeats mapSeats(Collection<ScreeningSeatDTO> seatDTOs, ScreeningId screeningId) {
        return new ScreeningSeats(
                seatDTOs.stream().map(seatDTO -> new ScreeningSeat(
                        screeningId,
                        seatDTO.row(),
                        seatDTO.column(),
                        SeatType.fromString(seatDTO.type())
                )).collect(Collectors.toSet())
        );
    }

    public Screening build() {
        Screening screening = Screening.create(cinemaId, movieId, screeningRoomId, time, title, screeningRoomNumber);
        screening.setSeats(mapSeats(seats, screening.getScreeningId()));
        return screening;
    }
}
