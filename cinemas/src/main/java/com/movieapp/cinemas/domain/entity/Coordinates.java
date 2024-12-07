package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record Coordinates(double latitude,
                          double longitude) {

    public Coordinates {
        if (!hasValidLatitude(latitude)) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (!hasValidLongitude(longitude)) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }

    private boolean hasValidLatitude(double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    private boolean hasValidLongitude(double longitude) {
        return longitude >= -180 && longitude <= 180;
    }

}
