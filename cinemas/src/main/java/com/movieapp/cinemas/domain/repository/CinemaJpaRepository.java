package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CinemaJpaRepository extends JpaRepository<Cinema, Long> {
    Optional<Cinema> findByName(String name);
    List<Cinema> findByCity(String city);
}
