package com.movieapp.screenings.domain.service;

import com.movieapp.screenings.application.dto.MovieDTO;
import com.movieapp.screenings.application.dto.ScreeningRoomDTO;
import com.movieapp.screenings.domain.exception.MovieDoesNotExistException;
import com.movieapp.screenings.domain.exception.OverlappingScreeningException;
import com.movieapp.screenings.domain.exception.ScreeningRoomDoesNotExistException;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.respository.ScreeningRepository;
import com.movieapp.screenings.interfaces.client.CinemasClient;
import com.movieapp.screenings.interfaces.client.MoviesClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
class ShowingDomainService implements ScreeningDomainService {
    private final ScreeningRepository repository;
    private final CinemasClient cinemasClient;
    private final MoviesClient moviesClient;

    ShowingDomainService(ScreeningRepository repository, CinemasClient cinemasClient, MoviesClient moviesClient) {
        this.repository = repository;
        this.cinemasClient = cinemasClient;
        this.moviesClient = moviesClient;
    }

    @Override
    public Screening createScreening(Screening screening) {
        Optional<ScreeningRoomDTO> screeningRoomDTO = cinemasClient.getScreeningRoomById(screening.getScreeningRoomId());
        Optional<MovieDTO> movieDTO = moviesClient.getMovieById(screening.getMovieId().id());

        if(screeningRoomDTO.isEmpty()) {
            log.error("Screening room with id: {} does not exist", screening.getScreeningRoomId());
            throw new ScreeningRoomDoesNotExistException("Screening room for Screening with id: " + screening.getScreeningRoomId() + " does not exist");
        }

        if(movieDTO.isEmpty()) {
            log.error("Movie with id: {} does not exist", screening.getMovieId().id());
            throw new MovieDoesNotExistException("Movie for Screening with id: " + screening.getMovieId().id() + " does not exist");
        }

        if (overlappingScreeningExistsInScreeningRoom(screening)) {
            log.debug("The Screening: {} overlaps with another screening in the same room", screening);
            throw new OverlappingScreeningException("Screening overlaps with another screening in the same room - please choose another time");
        }

        screening.setMovieTitle(movieDTO.get().title());
        return screening;
    }

    private boolean overlappingScreeningExistsInScreeningRoom(Screening screening) {
        return repository.findAllByScreeningRoomId(screening.getScreeningRoomId()).stream()
                .anyMatch(s -> s.getTime().overlaps(screening.getTime()));
    }

}
