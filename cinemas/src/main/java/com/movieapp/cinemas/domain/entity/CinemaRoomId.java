package com.movieapp.cinemas.domain.entity;

import org.springframework.util.Assert;

public record CinemaRoomId(Long id) {
    public CinemaRoomId {
        Assert.notNull(id, "Cinema room id must not be null");
    }
}
