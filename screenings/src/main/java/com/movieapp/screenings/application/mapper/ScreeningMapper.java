package com.movieapp.screenings.application.mapper;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.domain.model.*;
import com.movieapp.screenings.infrastructure.entity.ScreeningEntity;

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

    public static Screening entityToDomainModel(ScreeningEntity entity) {
        return new Screening(
                new ScreeningId(entity.getId()),
                new MovieId(entity.getMovieId()),
                new ScreeningRoomId(entity.getScreeningRoomId()),
                new ScreeningTime(entity.getStartTime(), entity.getEndTime())
        );
    }

    public static ScreeningEntity domainModelToEntity(Screening screening) {
        ScreeningEntity entity = new ScreeningEntity();
        entity.setId(screening.getScreeningId().id());
        entity.setMovieId(screening.getMovieId().id());
        entity.setScreeningRoomId(screening.getScreeningRoomId().id());
        entity.setStartTime(screening.getTime().getStartTime());
        entity.setEndTime(screening.getTime().getEndTime());
        return entity;
    }

}
