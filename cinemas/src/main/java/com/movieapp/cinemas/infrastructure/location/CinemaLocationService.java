package com.movieapp.cinemas.infrastructure.location;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.Coordinates;

public interface CinemaLocationService {
    Coordinates getCoordinates(String city, String street, String postalCode);
}
