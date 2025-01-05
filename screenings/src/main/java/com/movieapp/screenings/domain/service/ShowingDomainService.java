package com.movieapp.screenings.domain.service;

import com.movieapp.screenings.application.dto.ReservationDTO;
import com.movieapp.screenings.application.events.SeatsAlreadyLockedEvent;
import com.movieapp.screenings.domain.exception.OverlappingScreeningException;
import com.movieapp.screenings.domain.exception.ScreeningSeatsAlreadyBookedException;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningId;
import com.movieapp.screenings.domain.model.SeatId;
import com.movieapp.screenings.domain.repository.ScreeningRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
class ShowingDomainService implements ScreeningDomainService {
    private final ScreeningRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;

    ShowingDomainService(ScreeningRepository repository, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Screening createScreening(Screening screening) {
        if (overlappingScreeningExistsInScreeningRoom(screening)) {
            log.debug("The Screening: {} overlaps with another screening in the same room", screening);
            throw new OverlappingScreeningException("Screening overlaps with another screening in the same room - please choose another time");
        }

        return screening;
    }

    @Override
    public void lockSeats(ReservationDTO reservationDTO) {
        Screening screening = repository.findById(new ScreeningId(reservationDTO.screeningId()))
                .orElseThrow(() -> new EntityNotFoundException("Screening with id: " + reservationDTO.screeningId() + " does not exist"));
        List<SeatId> seatIds = reservationDTO.screeningSeatIds().stream()
                .map(SeatId::new)
                .toList();
        lockScreeningSeats(reservationDTO, screening, seatIds);
    }

    private boolean overlappingScreeningExistsInScreeningRoom(Screening screening) {
        return repository.findAllByScreeningRoomId(screening.getScreeningRoomId()).stream()
                .anyMatch(s -> s.getTime().overlaps(screening.getTime()));
    }

    private void lockScreeningSeats(ReservationDTO reservationDTO, Screening screening, List<SeatId> seatIds) {
        try {
            screening.lockSeats(seatIds);
            repository.save(screening);
        }
        catch (ScreeningSeatsAlreadyBookedException e) {
            log.error("Screening seats already booked: {}", e.getAlreadyReservedIds());
            applicationEventPublisher.publishEvent(new SeatsAlreadyLockedEvent(reservationDTO.id(), getMappedSeatIds(e.getAlreadyReservedIds())));
        }
    }

    private List<UUID> getMappedSeatIds(Set<SeatId> seatIds) {
        return seatIds.stream()
                .map(SeatId::id)
                .toList();
    }

}
