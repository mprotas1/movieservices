package com.movieapp.screenings.application.service;

import com.movieapp.screenings.application.dto.*;
import com.movieapp.screenings.application.mapper.ScreeningMapper;
import com.movieapp.screenings.application.mapper.SeatMapper;
import com.movieapp.screenings.domain.exception.MovieDoesNotExistException;
import com.movieapp.screenings.domain.exception.ScreeningRoomDoesNotExistException;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningId;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.model.ScreeningSeat;
import com.movieapp.screenings.domain.repository.ScreeningRepository;
import com.movieapp.screenings.domain.service.ScreeningDomainService;
import com.movieapp.screenings.interfaces.client.CinemasClient;
import com.movieapp.screenings.interfaces.client.MoviesClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
class ShowingApplicationService implements ScreeningApplicationService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ScreeningDomainService screeningDomainService;
    private final ScreeningRepository repository;
    private final CinemasClient cinemasClient;
    private final MoviesClient moviesClient;
    private final ScreeningMapper screeningMapper;
    private final ScreeningSeatService screeningSeatService;
    private final SeatMapper seatMapper;

    @Override
    @Transactional
    public ScreeningDTO createScreening(ScreeningCreateRequest request) {
        ScreeningRoomDTO screeningRoomDTO = getScreeningRoom(request.screeningRoomId());
        MovieDTO movieDTO = getMovie(request.movieId());
        Screening mapped = Screening.builder()
                .withMovieId(request.movieId())
                .withCinemaId(screeningRoomDTO.cinemaId())
                .withScreeningRoomId(request.screeningRoomId())
                .withScreeningTime(request.startTime(), movieDTO.duration())
                .withMovieTitle(movieDTO.title())
                .withScreeningRoomNumber(screeningRoomDTO.number())
                .withScreeningSeats(getMappedSeats(screeningRoomDTO.seats()))
                .build();
        Screening screening = screeningDomainService.createScreening(mapped);
        Screening withPricedSeats = createSeatsPricing(screening);
        Screening savedScreening = repository.save(withPricedSeats);
        log.debug("Created Screening: {}", savedScreening);
        return screeningMapper.toDTO(savedScreening);
    }

    private Screening createSeatsPricing(Screening screening) {
        ScreeningDTO screeningDTO = screeningMapper.toDTO(screening);
        return screeningSeatService.priceSeats(screeningDTO);
    }

    @Override
    public List<ScreeningDTO> findAll() {
        return repository.findAll().stream()
                .map(screeningMapper::toDTO)
                .toList();
    }

    @Override
    public ScreeningDTO findById(UUID screeningId) {
        return repository.findById(new ScreeningId(screeningId))
                .map(screeningMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Screening with id: " + screeningId + " does not exist"));
    }

    @Override
    public void lockSeats(ReservationDTO reservationDTO) {
        screeningDomainService.lockSeats(reservationDTO);
    }

    private ScreeningRoomDTO getScreeningRoom(UUID screeningRoomId) {
        ScreeningRoomId id = new ScreeningRoomId(screeningRoomId);
        return cinemasClient.getScreeningRoomById(id)
                .orElseThrow(() -> new ScreeningRoomDoesNotExistException("Screening room with id: " + screeningRoomId + " does not exist"));
    }

    private MovieDTO getMovie(Long movieId) {
        return moviesClient.getMovieById(movieId)
                .orElseThrow(() -> new MovieDoesNotExistException("Movie with id: " + movieId + " does not exist"));
    }

    private Collection<ScreeningSeat> getMappedSeats(List<ScreeningSeatDTO> seats) {
        return seats.stream()
                .map(seatMapper::toDomain)
                .toList();
    }

}
