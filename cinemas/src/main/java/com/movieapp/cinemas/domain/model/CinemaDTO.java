package com.movieapp.cinemas.domain.model;

import com.movieapp.cinemas.domain.entity.Cinema;
import org.bson.types.ObjectId;

public record CinemaDTO(ObjectId id, String name) {

    public static CinemaDTO fromEntity(Cinema cinema) {
        return new CinemaDTO(cinema.getId(), cinema.getName());
    }

}
