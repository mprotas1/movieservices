package com.movieapp.cinemas.domain.model;

import jakarta.validation.constraints.NotBlank;

public record CinemaInformation(@NotBlank String name,
                                AddressInformation address) {}
