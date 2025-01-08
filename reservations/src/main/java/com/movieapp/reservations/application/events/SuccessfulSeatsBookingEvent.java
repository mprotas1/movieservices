package com.movieapp.reservations.application.events;

import com.movieapp.reservations.application.dto.PricedSeatDTO;

import java.util.List;
import java.util.UUID;

public record SuccessfulSeatsBookingEvent(UUID reservationId, List<PricedSeatDTO> seats) {}
