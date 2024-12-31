package com.movieapp.cinemas.service.model;

import java.util.UUID;

public record SeatDTO(UUID id,
                      UUID screeningRoomId,
                      int row,
                      int column,
                      String type) {}
