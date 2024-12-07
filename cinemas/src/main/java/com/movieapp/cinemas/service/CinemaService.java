package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.service.model.CinemaDTO;
import com.movieapp.cinemas.service.model.CinemaInformation;
import jakarta.validation.Valid;

import java.util.List;

public interface CinemaService {
    List<CinemaDTO> findAll();
    List<CinemaDTO> findAll(int page, int size);
    CinemaDTO createCinema(@Valid CinemaInformation cinema);
    CinemaDTO findById(CinemaId id);
    CinemaDTO findByName(String name);
    void deleteById(CinemaId id);
}
