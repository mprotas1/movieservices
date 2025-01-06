package com.movieapp.screenings.application.dto;

import java.util.List;
import java.util.UUID;

public class SuccessfulSeatsBookingEvent {
    private final UUID reservationId;
    private final List<PricedSeatDTO> seats;

    public SuccessfulSeatsBookingEvent(UUID reservationId, List<PricedSeatDTO> seats) {
        this.reservationId = reservationId;
        this.seats = seats;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public List<PricedSeatDTO> getSeats() {
        return seats;
    }

}
