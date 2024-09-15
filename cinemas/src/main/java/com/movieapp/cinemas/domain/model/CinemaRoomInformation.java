package com.movieapp.cinemas.domain.model;

import jakarta.validation.constraints.Positive;

public record CinemaRoomInformation(@Positive Long cinemaId,
                                    @Positive int capacity) {

}
