package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record Coordinates(double latitude,
                          double longitude) {

    public Coordinates {
        if (!hasValidLatitude()) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (!hasValidLongitude()) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }

    private boolean hasValidLatitude() {
        return this.latitude >= -90 && this.latitude <= 90;
    }

    private boolean hasValidLongitude() {
        return this.longitude >= -180 && this.longitude <= 180;
    }

}
