package com.movieapp.cinemas.service.model;

import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CinemaRoomInformation(@Positive UUID cinemaId,
                                    @Positive int capacity) {}
