package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class CinemaRoomId {
    private Long id;

    public CinemaRoomId() {}

    public CinemaRoomId(Long cinemaRoomId) {
        setValue(cinemaRoomId);
    }

    public Long getValue() {
        return id;
    }

    public void setValue(Long id) {
        this.id = id;
    }

}