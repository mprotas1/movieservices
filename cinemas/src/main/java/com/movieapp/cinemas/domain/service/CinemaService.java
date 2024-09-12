package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import jakarta.validation.Valid;

public interface CinemaService {
    CinemaDTO createCinema(@Valid CinemaInformation cinema);
    CinemaDTO findById(Long id);
    CinemaDTO findByName(String name);
    void deleteById(Long id);
}
