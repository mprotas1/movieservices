package com.movieapp.reservations.application.dto;

import java.util.UUID;

public record PricedSeatDTO(
        UUID id,
        UUID screeningRoomId,
        int row,
        int column,
        String seatType,
        double price
) {}
