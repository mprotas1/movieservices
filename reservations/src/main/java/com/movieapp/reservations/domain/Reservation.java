package com.movieapp.reservations.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private ReservationId reservationId;
    private UserId userId;
    private ScreeningId screeningId;
    private List<SeatId> seatIds;
    private ReservationStatus status;
    private ReservationPrice price;

    public Reservation(ReservationId reservationId,
                       ScreeningId screeningId,
                       List<SeatId> seatIds,
                       UserId userId,
                       ReservationStatus status,
                       ReservationPrice price) {
        this.reservationId = reservationId;
        this.screeningId = screeningId;
        this.seatIds = seatIds;
        this.userId = userId;
        this.status = status;
        this.price = price;
    }

    public Reservation(ScreeningId screeningId,
                       List<SeatId> seatIds,
                       UserId userId) {
        this.reservationId = ReservationId.generate();
        this.screeningId = screeningId;
        this.seatIds = seatIds;
        this.userId = userId;
        this.status = ReservationStatus.PENDING;
    }

    public Reservation(ReservationId reservationId,
                       ScreeningId screeningId,
                       List<SeatId> list,
                       UserId userId,
                       ReservationStatus status) {
        this.reservationId = reservationId;
        this.screeningId = screeningId;
        this.seatIds = list;
        this.userId = userId;
        this.status = status;
    }

    public void confirm() {
        if(this.status == ReservationStatus.CANCELLED) {
            throw new InvalidReservationTransitionException("Cannot confirm a cancelled reservation");
        }
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }

    public void book(ReservationPrice price) {
        if(this.status == ReservationStatus.CANCELLED) {
            throw new InvalidReservationTransitionException("Cannot book a cancelled reservation");
        }
        this.price = price;
        this.status = ReservationStatus.BOOKED;
    }

}
