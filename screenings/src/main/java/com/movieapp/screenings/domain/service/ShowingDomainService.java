package com.movieapp.screenings.domain.service;

import com.movieapp.screenings.domain.exception.OverlappingScreeningException;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.repository.ScreeningRepository;
import com.movieapp.screenings.interfaces.client.CinemasClient;
import com.movieapp.screenings.interfaces.client.MoviesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        if (overlappingScreeningExistsInScreeningRoom(screening)) {
            log.debug("The Screening: {} overlaps with another screening in the same room", screening);
            throw new OverlappingScreeningException("Screening overlaps with another screening in the same room - please choose another time");
        }

        return screening;
    }

    private boolean overlappingScreeningExistsInScreeningRoom(Screening screening) {
        return repository.findAllByScreeningRoomId(screening.getScreeningRoomId()).stream()
                .anyMatch(s -> s.getTime().overlaps(screening.getTime()));
    }

}
