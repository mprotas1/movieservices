package com.movieapp.cinemas.infrastructure.location;

import com.movieapp.cinemas.domain.entity.Coordinates;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class InMemoryCinemaLocationService implements CinemaLocationService {

    @Override
    public Coordinates getCoordinates(String city, String street, String postalCode) {
        return new Coordinates(0.0, 0.0);
    }

}
