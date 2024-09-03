package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import org.bson.types.ObjectId;

public interface CinemaService {
    CinemaDTO createCinema(CinemaInformation cinema);
    CinemaDTO findById(ObjectId id);
    CinemaDTO findByName(String name);
    void deleteById(ObjectId id);
}
