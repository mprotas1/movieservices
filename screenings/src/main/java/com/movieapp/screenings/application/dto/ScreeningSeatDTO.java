package com.movieapp.screenings.application.dto;

import java.util.UUID;

public record ScreeningSeatDTO(UUID id,
                      UUID screeningId,
                      UUID screeningRoomId,
                      int row,
                      int column,
                      String type) {}