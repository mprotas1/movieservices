package com.movieapp.screenings.application.mapper;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.model.ScreeningTime;
import org.springframework.stereotype.Component;

public class ScreeningMapper {

    public static Screening toEntity(ScreeningCreateRequest request) {
        return new Screening(
            new MovieId(request.movieId()),
            new ScreeningRoomId(request.screeningRoomId()),
            ScreeningTime.from(request.startTime(), request.duration())
        );
    }

    public static Screening toEntity(ScreeningDTO dto) {
        return new Screening(
            new MovieId(dto.movieId()),
            new ScreeningRoomId(dto.screeningRoomId()),
            new ScreeningTime(dto.startTime(), dto.endTime())
        );
    }

    public static ScreeningDTO toDTO(Screening screening) {
        return new ScreeningDTO(
            screening.getScreeningId().id(),
            screening.getMovieId().id(),
            screening.getScreeningRoomId().id(),
            screening.getTime().getStartTime(),
            screening.getTime().getEndTime()
        );
    }

}
