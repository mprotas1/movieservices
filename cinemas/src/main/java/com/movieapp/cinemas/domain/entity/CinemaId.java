package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.UUID;

@Embeddable
@Getter @Setter
public class CinemaId {
    private UUID uuid;

    public CinemaId() {
        this.uuid = UUID.randomUUID();
    }

    public CinemaId(UUID uuid) {
        Assert.notNull(uuid, "Cinema id must not be null");
        this.uuid = uuid;
    }

}
