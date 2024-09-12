package com.movieapp.cinemas.domain.model;

import com.movieapp.cinemas.domain.entity.Cinema;

public record CinemaDTO(Long id, String name) {

    public static CinemaDTO fromEntity(Cinema cinema) {
        return new CinemaDTO(cinema.getId(), cinema.getName());
    }

}
