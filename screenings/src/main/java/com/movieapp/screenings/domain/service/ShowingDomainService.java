package com.movieapp.screenings.domain.service;

import com.movieapp.screenings.domain.exception.OverlappingScreeningException;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.respository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ShowingDomainService implements ScreeningDomainService {
    private final ScreeningRepository repository;

    @Override
    public Screening createScreening(Screening screening) {
        // check, if screening room is existing and not already occupied by microservice API call - write the code

        if (overlappingScreeningExistsInScreeningRoom(screening)) {
            throw new OverlappingScreeningException("Screening overlaps with another screening in the same room - please choose another time");
        }

        return screening;
    }

    private boolean overlappingScreeningExistsInScreeningRoom(Screening screening) {
        return repository.findAllByScreeningRoomId(screening.getScreeningRoomId()).stream()
                .anyMatch(s -> s.getTime().overlaps(screening.getTime()));
    }

}
