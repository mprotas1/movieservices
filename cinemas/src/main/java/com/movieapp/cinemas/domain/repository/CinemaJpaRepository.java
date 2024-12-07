package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CinemaJpaRepository extends JpaRepository<Cinema, CinemaId> {
    Optional<Cinema> findByName(String name);
    @Query("SELECT c FROM cinemas c WHERE c.address.city = :city")
    List<Cinema> findByCity(String city);
}
