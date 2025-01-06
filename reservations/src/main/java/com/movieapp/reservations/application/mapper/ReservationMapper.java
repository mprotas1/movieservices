package com.movieapp.reservations.application.mapper;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.dto.ReservationDTO;
import com.movieapp.reservations.domain.*;
import com.movieapp.reservations.infrastructure.entity.ReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationDTO toDTO(Reservation reservation) {
        return new ReservationDTO(reservation.getReservationId().getId(),
                reservation.getScreeningId().id(),
                reservation.getSeatIds().stream()
                                .map(SeatId::id)
                                .toList(),
                reservation.getUserId().id(),
                reservation.getStatus().name()
        );
    }

    public Reservation toDomain(ReservationCreateRequest request) {
        return new Reservation(
                new ScreeningId(request.screeningId()),
                request.seatIds().stream()
                        .map(SeatId::new)
                        .toList(),
                new UserId(request.userId())
        );
    }

    public Reservation toDomain(ReservationEntity entity) {
        return new Reservation(
                new ReservationId(entity.getId()),
                new ScreeningId(entity.getScreeningId()),
                entity.getSeats().stream()
                        .map(reservationSeatEntity -> new SeatId(reservationSeatEntity.getScreeningSeatId()))
                        .toList(),
                new UserId(entity.getUserId()),
                ReservationStatus.valueOf(entity.getStatus())
        );
    }

    public static ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getReservationId().getId(),
                reservation.getScreeningId().id(),
                reservation.getSeatIds().stream()
                        .map(SeatId::id)
                        .toList(),
                reservation.getUserId().id(),
                reservation.getStatus().name()
        );
    }

}
