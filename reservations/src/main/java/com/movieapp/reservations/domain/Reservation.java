package com.movieapp.reservations.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }

}
