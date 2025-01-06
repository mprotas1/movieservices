package com.movieapp.screenings.application.mapper;

import com.movieapp.screenings.application.dto.PricedSeatDTO;
import com.movieapp.screenings.application.dto.ScreeningSeatDTO;
import com.movieapp.screenings.application.dto.SeatType;
import com.movieapp.screenings.domain.model.*;
import com.movieapp.screenings.infrastructure.entity.ScreeningSeatEntity;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public ScreeningSeat toDomain(ScreeningSeatDTO dto) {
        return new ScreeningSeat(
                new SeatId(dto.id()),
                new ScreeningId(dto.screeningId()),
                dto.row(),
                dto.column(),
                SeatType.fromString(dto.type()),
                false,
                null
		);
    }

    public ScreeningSeat toDomain(ScreeningSeatEntity entity) {
        return new ScreeningSeat(
                new SeatId(entity.getId()),
                new ScreeningId(entity.getScreeningId()),
                entity.getRow(),
                entity.getColumn(),
                entity.getType(),
                entity.isReserved(),
                entity.getPrice()
        );
    }

    public ScreeningSeatDTO toDTO(ScreeningSeat seat, ScreeningRoomId screeningRoomId) {
        return new ScreeningSeatDTO(
                seat.getSeatId().id(),
                seat.getScreeningId().id(),
                screeningRoomId.id(),
                seat.getRow(),
                seat.getColumn(),
                seat.getSeatType().name()
        );
    }

    public ScreeningSeatEntity toEntity(ScreeningSeat screeningSeat) {
        return new ScreeningSeatEntity(
                screeningSeat.getSeatId().id(),
                screeningSeat.getScreeningId().id(),
                screeningSeat.getRow(),
                screeningSeat.getColumn(),
                screeningSeat.getSeatType(),
                screeningSeat.isReserved(),
                screeningSeat.getPrice()
        );
    }

    public PricedSeatDTO toPricedSeatDTO(ScreeningSeat seat) {
        return new PricedSeatDTO(
                seat.getSeatId().id(),
                seat.getScreeningId().id(),
                seat.getRow(),
                seat.getColumn(),
                seat.getSeatType().name(),
                seat.getPrice()
        );
    }
}
