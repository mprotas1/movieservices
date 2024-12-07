package com.movieapp.screenings.domain.model;

import java.util.UUID;

import static org.springframework.util.Assert.notNull;

public record ScreeningId(UUID id) {
    public ScreeningId {
        notNull(id, "ScreeningId must not be null");
    }
}
