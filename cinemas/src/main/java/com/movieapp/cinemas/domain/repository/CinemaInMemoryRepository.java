package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CinemaInMemoryRepository implements CinemaRepository {
    private final Map<CinemaId, Cinema> cinemas;

    public CinemaInMemoryRepository() {
        this.cinemas = new HashMap<>();
    }

    @Override
    public Optional<Cinema> findById(CinemaId id) {
        return cinemas.entrySet().stream()
                .filter(entry -> entry.getKey().equals(id))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public Optional<Cinema> findByName(String name) {
        return cinemas.values().stream()
                .filter(cinema -> cinema.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Cinema> findAll() {
        return new ArrayList<>(cinemas.values());
    }

    @Override
    public List<Cinema> findAll(int page, int size) {
        List<List<Cinema>> partitionedCinemas = new ArrayList<>();
        List<Cinema> cinemasList = new ArrayList<>(cinemas.values());
        for (int i = 0; i < cinemasList.size(); i += size) {
            partitionedCinemas.add(cinemasList.subList(i, Math.min(i + size, cinemasList.size())));
        }
        return partitionedCinemas.get(page);
    }

    @Override
    public List<Cinema> findByCity(String city) {
        return cinemas.values().stream()
                .filter(cinema -> cinema.getAddress().getCity().equals(city))
                .toList();
    }

    @Override
    public Cinema save(Cinema cinema) {
        return cinemas.put(cinema.getId(), cinema);
    }

    @Override
    public void deleteById(CinemaId id) {
        cinemas.remove(id);
    }

    @Override
    public void deleteAll() {
        cinemas.clear();
    }
}
