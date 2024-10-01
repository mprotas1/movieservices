package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Cinema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class JpaCinemaRepository implements CinemaRepository {
    private final CinemaJpaRepository cinemaJpaRepository;

    @Override
    public Optional<Cinema> findById(Long id) {
        return cinemaJpaRepository.findById(id);
    }

    @Override
    public Optional<Cinema> findByName(String name) {
        return cinemaJpaRepository.findByName(name);
    }

    @Override
    public List<Cinema> findAll() {
        return cinemaJpaRepository.findAll();
    }

    @Override
    public List<Cinema> findByCity(String city) {
        return cinemaJpaRepository.findByCity(city);
    }

}
