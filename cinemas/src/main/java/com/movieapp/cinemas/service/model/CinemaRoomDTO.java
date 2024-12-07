package com.movieapp.cinemas.service.model;

import java.util.UUID;

public record CinemaRoomDTO(UUID roomId, UUID cinemaId, int number, int capacity) {}
