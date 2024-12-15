package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class MoviesRestClient implements MoviesClient {
    private final RestTemplate restTemplate;

    @Override
    public Optional<MovieDTO> getMovieById(Long id) {
        ResponseEntity<MovieDTO> movieResponseEntity = restTemplate.getForEntity(buildUrl(id), MovieDTO.class);

        if(movieResponseEntity.getStatusCode().isError()) {
            return Optional.empty();
        }

        return Optional.ofNullable(movieResponseEntity.getBody());
    }

    private String buildUrl(Long id) {
        return "http://localhost:8000/movies/" + id;
    }

}
