package com.movieapp.reservations.application.mapper;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.dto.ReservationDTO;
import com.movieapp.reservations.domain.Reservation;
import com.movieapp.reservations.domain.ScreeningId;
import com.movieapp.reservations.domain.SeatId;
import com.movieapp.reservations.domain.UserId;
import com.movieapp.reservations.infrastructure.entity.ReservationEntity;
import com.movieapp.reservations.infrastructure.entity.SeatEntity;
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

    public ReservationDTO toDTO(ReservationEntity entity) {
        return new ReservationDTO(entity.getId(),
                entity.getScreeningId(),
                entity.getSeats().stream()
                        .map(SeatEntity::getId)
                        .toList(),
                entity.getUserId(),
                entity.getStatus()
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
                new ScreeningId(entity.getScreeningId()),
                entity.getSeats().stream()
                        .map(seatEntity -> new SeatId(seatEntity.getId()))
                        .toList(),
                new UserId(entity.getUserId())
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
