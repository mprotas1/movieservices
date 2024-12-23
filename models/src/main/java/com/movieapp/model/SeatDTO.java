package com.movieapp.model;

import java.util.UUID;

public record SeatDTO(UUID id,
                      UUID screeningRoomId,
                      int row,
                      int column,
                      String type,
                      double price) {}
