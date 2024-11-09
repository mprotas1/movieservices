package com.movieapp.screenings.domain.service;

import com.movieapp.screenings.domain.exception.OverlappingScreeningException;
import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.model.ScreeningTime;
import com.movieapp.screenings.domain.respository.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowingDomainServiceTest {
    @InjectMocks
    private ShowingDomainService showingDomainService;

    @Mock
    private ScreeningRepository screeningRepository;

    @Test
    void shouldRejectWhenOverlappingScreeningExistsInScreeningRoom() {
        Instant now = Instant.now();
        ScreeningTime time = ScreeningTime.from(now.plus(5, ChronoUnit.SECONDS), 65);
        ScreeningTime overlappingTime = ScreeningTime.from(now.plus(5, ChronoUnit.MINUTES), 55);

        Screening targetScreening = new Screening(
                new MovieId(UUID.randomUUID()),
                new ScreeningRoomId(UUID.randomUUID()),
                overlappingTime
        );

        Screening existingOverlappingScreening = new Screening(
                new MovieId(UUID.randomUUID()),
                new ScreeningRoomId(UUID.randomUUID()),
                time
        );

        when(screeningRepository.findAllByScreeningRoomId(any(ScreeningRoomId.class)))
                .thenReturn(List.of(existingOverlappingScreening));

        assertThrows(OverlappingScreeningException.class, () -> showingDomainService.createScreening(targetScreening));
    }

    @Test
    void shouldAcceptWhenNoOverlappingScreeningExistsInScreeningRoom() {
        Instant now = Instant.now();
        ScreeningTime time = ScreeningTime.from(now.plus(5, ChronoUnit.SECONDS), 65);

        Screening targetScreening = Screening.builder()
                .withMovieId(UUID.randomUUID())
                .withScreeningRoomId(UUID.randomUUID())
                .withStartTime(Instant.now().plus(1, ChronoUnit.DAYS))
                .withDuration(85)
                .build();

        Screening existingNonOverlappingScreening = new Screening(
                new MovieId(UUID.randomUUID()),
                new ScreeningRoomId(UUID.randomUUID()),
                time
        );

        when(screeningRepository.findAllByScreeningRoomId(any(ScreeningRoomId.class)))
                .thenReturn(List.of(existingNonOverlappingScreening));

        showingDomainService.createScreening(targetScreening);
    }

}
