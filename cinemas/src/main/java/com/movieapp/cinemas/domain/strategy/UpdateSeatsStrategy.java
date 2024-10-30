package com.movieapp.cinemas.domain.strategy;

import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.Seat;

import java.util.List;

public interface UpdateSeatsStrategy {
    List<Seat> updateSeats(CinemaRoom room, int newCapacity);
}
