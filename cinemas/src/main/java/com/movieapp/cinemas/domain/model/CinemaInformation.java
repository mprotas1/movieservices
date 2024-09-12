package com.movieapp.cinemas.domain.model;

import jakarta.validation.constraints.NotBlank;

public record CinemaInformation(@NotBlank(message = "Name must not be blank") String name,
                                AddressInformation address) {}
