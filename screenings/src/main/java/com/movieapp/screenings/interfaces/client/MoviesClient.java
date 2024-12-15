package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.MovieDTO;

import java.util.Optional;

public interface MoviesClient {
    Optional<MovieDTO> getMovieById(Long id);
}
