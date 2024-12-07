package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class CinemaRoomId {
    private UUID uuid;

    public CinemaRoomId() {
        this.uuid = UUID.randomUUID();
    }

    public CinemaRoomId(UUID cinemaRoomId) {
        setValue(cinemaRoomId);
    }

    public UUID getValue() {
        return uuid;
    }

    public void setValue(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CinemaRoomId)) return false;
        CinemaRoomId that = (CinemaRoomId) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}