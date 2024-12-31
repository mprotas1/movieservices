package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.*;
import com.movieapp.cinemas.service.model.SeatDTO;
import org.springframework.stereotype.Component;

@Component
class SeatConcreteMapper implements SeatsMapper {

    @Override
    public SeatDTO toDTO(Seat seat) {
        return new SeatDTO(
                seat.getId().getId(),
                seat.getRoom().getId().getValue(),
                seat.getPosition().rowNumber(),
                seat.getPosition().seatNumber(),
                seat.getSeatType().name()
        );
    }

    @Override
    public Seat toEntity(SeatDTO seatDTO, CinemaRoom room) {
        return new Seat(
                new SeatId(seatDTO.id()),
                new SeatPosition(seatDTO.row(), seatDTO.column()),
                SeatType.fromString(seatDTO.type()),
                room
        );
    }

}
