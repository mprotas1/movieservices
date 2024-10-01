package com.movieapp.cinemas.domain.entity;

import org.springframework.util.Assert;

public record CinemaId(Long id) {

    public CinemaId {
        Assert.notNull(id, "Cinema id must not be null");
    }

}
