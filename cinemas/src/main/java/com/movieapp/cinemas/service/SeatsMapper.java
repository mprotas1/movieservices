package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.Seat;
import com.movieapp.cinemas.service.model.SeatDTO;

public interface SeatsMapper {
    SeatDTO toDTO(Seat seat);
    Seat toEntity(SeatDTO seatDTO, CinemaRoom room);
}
