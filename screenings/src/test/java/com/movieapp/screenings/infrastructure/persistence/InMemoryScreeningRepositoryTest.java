package com.movieapp.screenings.infrastructure.persistence;

import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryScreeningRepositoryTest {
    private InMemoryScreeningRepository inMemoryScreeningRepository;
    private Screening screening;

    @BeforeEach
    void setUp() {
        inMemoryScreeningRepository = new InMemoryScreeningRepository();
        screening = Screening.builder().withScreeningRoomId(UUID.randomUUID())
                .withMovieId(1L)
                .withScreeningTime(Instant.now().plus(5, ChronoUnit.MINUTES), 120)
                .build();
    }

    @Test
    @DisplayName("In memory repository should add screening")
    void shouldAddScreening() {
        var savedScreening = inMemoryScreeningRepository.save(screening);
        assertEquals(screening, savedScreening);
    }

    @Test
    @DisplayName("In memory repository should find screening by reservationId")
    void shouldFindScreeningById() {
        Screening saved = inMemoryScreeningRepository.save(screening);
        var foundScreening = inMemoryScreeningRepository.findById(saved.getScreeningId());
        assertTrue(foundScreening.isPresent());
        assertEquals(saved, foundScreening.get());
    }

    @Test
    @DisplayName("In memory repository should not find screening by non-existing reservationId")
    void shouldNotFindScreeningByNonExistingId() {
        ScreeningId screeningId = new ScreeningId(UUID.randomUUID());
        var foundScreening = inMemoryScreeningRepository.findById(screeningId);
        assertTrue(foundScreening.isEmpty());
    }

    @Test
    @DisplayName("In memory repository should find all screenings")
    void shouldFindAllScreenings() {
        inMemoryScreeningRepository.save(screening);
        var screenings = inMemoryScreeningRepository.findAll();
        assertEquals(1, screenings.size());
        assertEquals(screening, screenings.getFirst());
    }

    @Test
    @DisplayName("In memory repository should find all screenings by movie reservationId")
    void shouldFindAllScreeningsByMovieId() {
        Screening saved = inMemoryScreeningRepository.save(screening);
        var screenings = inMemoryScreeningRepository.findAllByMovieId(saved.getMovieId());
        assertEquals(1, screenings.size());
        assertEquals(screening, screenings.get(0));
    }

    @Test
    @DisplayName("In memory repository should find all screenings by screening room reservationId")
    void shouldFindAllScreeningsByScreeningRoomId() {
        Screening saved = inMemoryScreeningRepository.save(screening);
        var screenings = inMemoryScreeningRepository.findAllByScreeningRoomId(saved.getScreeningRoomId());
        assertEquals(1, screenings.size());
        assertEquals(screening, screenings.get(0));
    }

    @Test
    @DisplayName("In memory repository should delete screening by reservationId")
    void shouldDeleteScreeningById() {
        Screening saved = inMemoryScreeningRepository.save(screening);
        inMemoryScreeningRepository.deleteById(saved.getScreeningId());
        var screenings = inMemoryScreeningRepository.findAll();
        assertTrue(screenings.isEmpty());
    }

    @Test
    @DisplayName("In memory repository should delete all screenings")
    void shouldDeleteAllScreenings() {
        inMemoryScreeningRepository.save(screening);
        inMemoryScreeningRepository.deleteAll();
        var screenings = inMemoryScreeningRepository.findAll();
        assertTrue(screenings.isEmpty());
    }

}
