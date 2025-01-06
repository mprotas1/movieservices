package com.movieapp.reservations.application.dto;

import java.util.List;
import java.util.UUID;

public record SuccessfulSeatsBookingEvent(UUID reservationId, List<PricedSeatDTO> seats) {

}
