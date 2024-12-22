package com.movieapp.screenings.application.mapper;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.model.ScreeningTime;
import com.movieapp.screenings.infrastructure.entity.ScreeningEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScreeningMapperTest {

    @Test
    @DisplayName("Should map ScreeningCreateRequest to Screening entity")
    void shouldMapCreateRequestToEntity() {
        MovieId movieId = new MovieId(1L);
        ScreeningRoomId screeningRoomId = new ScreeningRoomId(randomUUID());
        Instant startTime = Instant.now().plus(5, ChronoUnit.MINUTES);
        int duration = 120;

        ScreeningCreateRequest request = new ScreeningCreateRequest(movieId.id(), screeningRoomId.id(), startTime);

        Screening screening = Screening.builder()
                .withMovieId(request.movieId())
                .withScreeningRoomId(request.screeningRoomId())
                .withScreeningTime(startTime, duration)
                .build();

        assertNotNull(screening);
        assertEquals(movieId, screening.getMovieId());
        assertEquals(screeningRoomId, screening.getScreeningRoomId());
        assertEquals(startTime, screening.getTime().getStartTime());
        assertEquals(startTime.plus(duration, ChronoUnit.MINUTES), screening.getTime().getEndTime());
    }

    @Test
    @DisplayName("Should map ScreeningDTO to Screening entity")
    void shouldMapScreeningDTOToEntity() {
        UUID screeningId = randomUUID();
        MovieId movieId = new MovieId(1L);
        ScreeningRoomId screeningRoomId = new ScreeningRoomId(randomUUID());
        Instant startTime = Instant.now().plus(5, ChronoUnit.MINUTES);
        Instant endTime = startTime.plus(120, ChronoUnit.MINUTES);

        ScreeningDTO dto = new ScreeningDTO(screeningId, movieId.id(), screeningRoomId.id(), startTime, endTime, "Movie Title", 1);

        assertNotNull(dto);
        assertEquals(screeningId, dto.screeningId());
        assertEquals(movieId.id(), dto.movieId());
        assertEquals(screeningRoomId.id(), dto.screeningRoomId());
        assertEquals(startTime, dto.startTime());
        assertEquals(endTime, dto.endTime());
    }

    // Test should map Screening entity to ScreeningDTO
    @Test
    @DisplayName("Should map Screening entity to ScreeningDTO")
    void shouldMapEntityToDTO() {
        MovieId movieId = new MovieId(1L);
        ScreeningRoomId screeningRoomId = new ScreeningRoomId(randomUUID());
        Instant startTime = Instant.now().plus(5, ChronoUnit.MINUTES);
        Instant endTime = startTime.plus(120, ChronoUnit.MINUTES);

        Screening screening = new Screening(movieId, screeningRoomId, new ScreeningTime(startTime, endTime), "Some title", 1);
        ScreeningDTO dto = ScreeningMapper.toDTO(screening);

        assertNotNull(dto);
        assertEquals(screening.getScreeningId().id(), dto.screeningId());
        assertEquals(movieId.id(), dto.movieId());
        assertEquals(screeningRoomId.id(), dto.screeningRoomId());
        assertEquals(startTime, dto.startTime());
        assertEquals(endTime, dto.endTime());
    }

    @Test
    @DisplayName("Should map ScreeningEntity to Screening domain model")
    void shouldMapEntityToScreeningDomainModel() {
        UUID screeningId = randomUUID();
        Long movieId = 1L;
        UUID screeningRoomId = randomUUID();
        Instant startTime = Instant.now().plus(5, ChronoUnit.MINUTES);
        Instant endTime = startTime.plus(120, ChronoUnit.MINUTES);

        ScreeningEntity entity = new ScreeningEntity();
        entity.setId(screeningId);
        entity.setMovieId(1L);
        entity.setScreeningRoomId(screeningRoomId);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);

        Screening domainModel = ScreeningMapper.entityToDomainModel(entity);

        assertNotNull(domainModel);
        assertEquals(screeningId, domainModel.getScreeningId().id());
        assertEquals(movieId, domainModel.getMovieId().id());
        assertEquals(screeningRoomId, domainModel.getScreeningRoomId().id());
        assertEquals(startTime, domainModel.getTime().getStartTime());
        assertEquals(endTime, domainModel.getTime().getEndTime());
    }

    @Test
    @DisplayName("Should map Screening domain model to ScreeningEntity")
    void shouldMapDomainModelToEntity() {
        Long movieId = 1L;
        UUID screeningRoomId = randomUUID();
        Instant startTime = Instant.now().plus(5, ChronoUnit.MINUTES);
        Instant endTime = startTime.plus(120, ChronoUnit.MINUTES);

        Screening domainModel = new Screening(
                new MovieId(1L),
                new ScreeningRoomId(screeningRoomId),
                new ScreeningTime(startTime, endTime),
                "Some title",
                1
        );

        ScreeningEntity entity = ScreeningMapper.domainModelToEntity(domainModel);

        assertNotNull(entity);
        assertEquals(domainModel.getScreeningId().id(), entity.getId());
        assertEquals(movieId, entity.getMovieId());
        assertEquals(screeningRoomId, entity.getScreeningRoomId());
        assertEquals(startTime, entity.getStartTime());
        assertEquals(endTime, entity.getEndTime());
    }

}
