package com.movieapp.screenings.application.mapper;

import com.movieapp.screenings.application.dto.SeatDTO;
import com.movieapp.screenings.domain.model.ScreeningId;
import com.movieapp.screenings.domain.model.ScreeningSeat;
import com.movieapp.screenings.domain.model.SeatId;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public ScreeningSeat toDomain(SeatDTO dto, ScreeningId screeningId) {
        return new ScreeningSeat(
                new SeatId(dto.id()),
                screeningId,
                dto.row(),
                dto.column()
        );
    }

    public SeatDTO toDTO(ScreeningSeat seat) {
        return new SeatDTO(
                seat.getSeatId().id(),
                seat.getScr,
                seat.row(),
                seat.column(),
                seat.seatType().name()
        );
    }

}
