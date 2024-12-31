package com.movieapp.screenings.domain.model;

public record Seat(
        SeatId seatId,
        ScreeningId screeningId,
        int row,
        int column,
        boolean isReserved
) {}
