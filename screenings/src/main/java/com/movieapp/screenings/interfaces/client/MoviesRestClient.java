package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class MoviesRestClient implements MoviesClient {
    @Value("${movies.service.url}")
    private String MOVIES_SERVICE_URL;
    private final RestTemplate restTemplate;

    @Override
    public Optional<MovieDTO> getMovieById(Long id) {
        ResponseEntity<MovieDTO> movieResponseEntity = restTemplate.getForEntity(buildUrl(id), MovieDTO.class);

        if(movieResponseEntity.getStatusCode().isError()) {
            log.error("Error while fetching movie with id: {}", id);
            return Optional.empty();
        }

        MovieDTO body = movieResponseEntity.getBody();
        log.debug("Fetched movie with id: {}", id);
        return Optional.ofNullable(body);
    }

    private String buildUrl(Long id) {
        return MOVIES_SERVICE_URL + id;
    }

}
