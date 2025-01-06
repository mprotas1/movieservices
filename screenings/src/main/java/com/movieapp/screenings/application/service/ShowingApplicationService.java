package com.movieapp.screenings.application.service;

import com.movieapp.screenings.application.dto.*;
import com.movieapp.screenings.application.events.SeatsAlreadyLockedEvent;
import com.movieapp.screenings.application.events.SuccessfulReservationSeatsBookedEvent;
import com.movieapp.screenings.application.mapper.ScreeningMapper;
import com.movieapp.screenings.application.mapper.SeatMapper;
import com.movieapp.screenings.domain.exception.MovieDoesNotExistException;
import com.movieapp.screenings.domain.exception.ScreeningRoomDoesNotExistException;
import com.movieapp.screenings.domain.exception.ScreeningSeatsAlreadyBookedException;
import com.movieapp.screenings.domain.model.*;
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
import java.util.Set;
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
                .withScreeningSeats(screeningRoomDTO.seats())
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
                .orElseThrow(() -> new EntityNotFoundException("Screening with reservationId: " + screeningId + " does not exist"));
    }

    @Override
    @Transactional
    public void lockSeats(ReservationDTO reservationDTO) {
        Screening screening = repository.findById(new ScreeningId(reservationDTO.screeningId()))
                .orElseThrow(() -> new EntityNotFoundException("Screening with id: " + reservationDTO.screeningId() + " does not exist"));
        List<SeatId> seatIds = reservationDTO.screeningSeatIds().stream()
                .map(SeatId::new)
                .toList();

        try {
            Set<ScreeningSeat> bookedSeats = screeningDomainService.lockSeats(screening, seatIds);
            log.debug("Locked seats: {} for Screening: {}", seatIds, screening);
            applicationEventPublisher.publishEvent(new SuccessfulSeatsBookingEvent(reservationDTO.id(), bookedSeats.stream()
                    .map(seatMapper::toPricedSeatDTO)
                    .toList()));
        }
        catch (ScreeningSeatsAlreadyBookedException e) {
            log.error("Screening seats already booked: {}", e.getAlreadyReservedIds());
            applicationEventPublisher.publishEvent(new SeatsAlreadyLockedEvent(reservationDTO.id(), e.getAlreadyReservedIds()));
        }
    }

    private ScreeningRoomDTO getScreeningRoom(UUID screeningRoomId) {
        ScreeningRoomId id = new ScreeningRoomId(screeningRoomId);
        return cinemasClient.getScreeningRoomById(id)
                .orElseThrow(() -> new ScreeningRoomDoesNotExistException("Screening room with reservationId: " + screeningRoomId + " does not exist"));
    }

    private MovieDTO getMovie(Long movieId) {
        return moviesClient.getMovieById(movieId)
                .orElseThrow(() -> new MovieDoesNotExistException("Movie with reservationId: " + movieId + " does not exist"));
    }

}
