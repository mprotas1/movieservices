package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CinemaReadRepository {
    Optional<Cinema> findById(Long id);
    Optional<Cinema> findByName(String name);
    List<Cinema> findAll();
    List<Cinema> findByCity(String city);
}
