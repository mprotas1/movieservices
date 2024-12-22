package com.movieapp.reservations.application.mapper;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.dto.ReservationDTO;
import com.movieapp.reservations.domain.Reservation;
import com.movieapp.reservations.domain.ScreeningId;
import com.movieapp.reservations.domain.SeatId;
import com.movieapp.reservations.domain.UserId;
import com.movieapp.reservations.infrastructure.entity.ReservationEntity;

public class ReservationMapper {

    public static ReservationDTO toDTO(Reservation reservation) {
        return new ReservationDTO(reservation.getReservationId().getId(),
                reservation.getScreeningId().id(),
                reservation.getSeatId().id(),
                reservation.getUserId().id(),
                reservation.getStatus().name()
        );
    }

    public static ReservationDTO toDTO(ReservationEntity entity) {
        return new ReservationDTO(entity.getId(),
                entity.getScreeningId(),
                entity.getSeatId(),
                entity.getUserId(),
                entity.getStatus()
        );
    }

    public static Reservation toDomain(ReservationCreateRequest request) {
        return new Reservation(
                new ScreeningId(request.screeningId()),
                new SeatId(request.seatId()),
                new UserId(request.userId())
        );
    }

    public static Reservation toDomain(ReservationEntity entity) {
        return new Reservation(
                new ScreeningId(entity.getScreeningId()),
                new SeatId(entity.getSeatId()),
                new UserId(entity.getUserId())
        );
    }

    public static ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getScreeningId().id(),
                reservation.getSeatId().id(),
                reservation.getUserId().id(),
                reservation.getStatus().name()
        );
    }

}
