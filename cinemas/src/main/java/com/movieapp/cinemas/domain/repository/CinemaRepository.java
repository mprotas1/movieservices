package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CinemaRepository {
    Optional<Cinema> findById(CinemaId id);
    Optional<Cinema> findByName(String name);
    List<Cinema> findAll();
    List<Cinema> findAll(int page, int size);
    List<Cinema> findByCity(String city);
    Cinema save(Cinema cinema);
    void deleteById(CinemaId id);
    void deleteAll();
}
