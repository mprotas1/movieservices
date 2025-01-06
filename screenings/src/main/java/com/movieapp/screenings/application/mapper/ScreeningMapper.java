package com.movieapp.screenings.application.mapper;

import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.domain.model.*;
import com.movieapp.screenings.infrastructure.entity.ScreeningEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ScreeningMapper {
    private final SeatMapper seatMapper;

    public ScreeningDTO toDTO(Screening screening) {
        return new ScreeningDTO(
                screening.getScreeningId().id(),
                screening.getMovieId().id(),
                screening.getScreeningRoomId().id(),
                screening.getCinemaId().id(),
                screening.getTime().getStartTime(),
                screening.getTime().getEndTime(),
                screening.getMovieTitle(),
                screening.getScreeningRoomNumber(),
                screening.getSeats().screeningSeats().stream()
                        .map(seat -> seatMapper.toDTO(seat, screening.getScreeningRoomId()))
                        .collect(Collectors.toList())
        );
    }

    public Screening entityToDomainModel(ScreeningEntity entity) {
        List<ScreeningSeat> screeningSeats = entity.getSeats().stream()
                .map(seatMapper::toDomain)
                .toList();
        return new Screening(
                new ScreeningId(entity.getId()),
                new MovieId(entity.getMovieId()),
                new CinemaId(entity.getCinemaId()),
                new ScreeningRoomId(entity.getScreeningRoomId()),
                new ScreeningTime(entity.getStartTime(), entity.getEndTime()),
                entity.getMovieTitle(),
                entity.getScreeningRoomNumber(),
                new ScreeningSeats(new HashSet<>(screeningSeats))
        );
    }

    public ScreeningEntity domainModelToEntity(Screening screening) {
        ScreeningEntity entity = new ScreeningEntity();
        entity.setId(screening.getScreeningId().id());
        entity.setMovieId(screening.getMovieId().id());
        entity.setScreeningRoomId(screening.getScreeningRoomId().id());
        entity.setStartTime(screening.getTime().getStartTime());
        entity.setEndTime(screening.getTime().getEndTime());
        entity.setMovieTitle(screening.getMovieTitle());
        entity.setScreeningRoomNumber(screening.getScreeningRoomNumber());
        entity.setCinemaId(screening.getCinemaId().id());
        entity.setSeats(screening.getSeats().screeningSeats().stream()
                .map(seatMapper::toEntity)
                .collect(Collectors.toSet()));
        return entity;
    }

    public Screening toDomain(ScreeningDTO screeningDTO) {
        return new Screening(
                new ScreeningId(screeningDTO.screeningId()),
                new MovieId(screeningDTO.movieId()),
                new CinemaId(screeningDTO.cinemaId()),
                new ScreeningRoomId(screeningDTO.screeningRoomId()),
                new ScreeningTime(screeningDTO.startTime(), screeningDTO.endTime()),
                screeningDTO.movieTitle(),
                screeningDTO.screeningRoomNumber(),
                new ScreeningSeats(screeningDTO.seats().stream()
                        .map(seatMapper::toDomain)
                        .collect(Collectors.toSet())
                )
        );
    }
}
