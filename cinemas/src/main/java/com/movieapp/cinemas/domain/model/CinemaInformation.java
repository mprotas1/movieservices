package com.movieapp.cinemas.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CinemaInformation(@NotBlank(message = "Name must not be blank") String name,
                                @Valid AddressInformation address) {}
