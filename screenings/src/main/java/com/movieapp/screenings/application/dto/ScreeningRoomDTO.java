package com.movieapp.screenings.application.dto;

import java.util.UUID;

public record ScreeningRoomDTO(UUID roomId, UUID cinemaId, int number, int capacity) {}
