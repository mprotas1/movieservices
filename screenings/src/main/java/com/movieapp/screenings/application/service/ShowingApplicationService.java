package com.movieapp.screenings.application.service;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.model.ScreeningTime;
import com.movieapp.screenings.domain.service.ScreeningDomainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ShowingApplicationService implements ScreeningApplicationService {
    private final ScreeningDomainService screeningDomainService;

    @Override
    public ScreeningDTO createScreening(ScreeningCreateRequest request) {
        Screening mapped = new Screening(
            new MovieId(request.movieId()),
            new ScreeningRoomId(request.screeningRoomId()),
            ScreeningTime.from(
                request.startTime(),
                    request.duration()
            )
        );
        Screening screening = screeningDomainService.createScreening(mapped);
        return new ScreeningDTO(
            screening.getScreeningId().id(),
            screening.getMovieId().id(),
            screening.getScreeningRoomId().id(),
            screening.getTime().getStartTime(),
                screening.getTime().getEndTime()
        );
    }

}
