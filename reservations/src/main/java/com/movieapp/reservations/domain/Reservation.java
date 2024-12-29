package com.movieapp.reservations.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private ReservationId reservationId;
    private UserId userId;
    private ScreeningId screeningId;
    private SeatId seatId;
    private ReservationStatus status;

    public Reservation(ScreeningId screeningId, SeatId seatId, UserId userId) {
        this.reservationId = ReservationId.generate();
        this.screeningId = screeningId;
        this.seatId = seatId;
        this.userId = userId;
        this.status = ReservationStatus.PENDING;
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

}
