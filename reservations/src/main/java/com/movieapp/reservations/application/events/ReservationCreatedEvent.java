package com.movieapp.reservations.application.events;

import com.movieapp.reservations.application.dto.ReservationDTO;

public record ReservationCreatedEvent(ReservationDTO dto) {}
