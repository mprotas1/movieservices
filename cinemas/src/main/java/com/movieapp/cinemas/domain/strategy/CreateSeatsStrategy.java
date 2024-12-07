package com.movieapp.cinemas.domain.strategy;

import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.Seat;

import java.util.List;

public interface CreateSeatsStrategy {
    List<Seat> createSeats(CinemaRoom room);
}
