package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Cinema;

import java.util.List;
import java.util.Optional;

public interface CinemaRepository {
    Optional<Cinema> findById(Long id);
    Optional<Cinema> findByName(String name);
    List<Cinema> findAll();
    List<Cinema> findByCity(String city);
    Optional<Cinema> save(Cinema cinema);
    void deleteById(Long id);
}
