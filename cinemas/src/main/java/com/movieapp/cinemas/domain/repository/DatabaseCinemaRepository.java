package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
class DatabaseCinemaRepository implements CinemaRepository {
    private final CinemaJpaRepository cinemaJpaRepository;

    @Override
    public Optional<Cinema> findById(CinemaId id) {
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
    public List<Cinema> findAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return cinemaJpaRepository.findAll(pageable).toList();
    }

    @Override
    public List<Cinema> findByCity(String city) {
        return cinemaJpaRepository.findByCity(city);
    }

    @Override
    public Cinema save(Cinema cinema) {
        return cinemaJpaRepository.save(cinema);
    }

    @Override
    public void deleteById(CinemaId id) {
        cinemaJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        cinemaJpaRepository.deleteAll();
    }

}
