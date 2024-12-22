package com.movieapp.screenings.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MovieDTO(Long id,
                       String title,
                       String description,
                       @JsonProperty("duration_in_minutes")
                       int duration) {}
