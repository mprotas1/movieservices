package com.movieapp.screenings.domain.model;

import java.util.Set;

public record ScreeningSeats(
        Set<Seat> seats
) {}
