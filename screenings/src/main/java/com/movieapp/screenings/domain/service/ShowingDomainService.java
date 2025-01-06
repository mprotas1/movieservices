package com.movieapp.screenings.domain.service;

import com.movieapp.screenings.domain.exception.OverlappingScreeningException;
import com.movieapp.screenings.domain.exception.ScreeningSeatsAlreadyBookedException;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningSeat;
import com.movieapp.screenings.domain.model.SeatId;
import com.movieapp.screenings.domain.repository.ScreeningRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
class ShowingDomainService implements ScreeningDomainService {
    private final ScreeningRepository repository;

    ShowingDomainService(ScreeningRepository repository) {
        this.repository = repository;
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
    public Set<ScreeningSeat> lockSeats(Screening screening, List<SeatId> seatIds) throws ScreeningSeatsAlreadyBookedException {
        screening.lockSeats(seatIds);
        Screening saved = repository.save(screening);
        log.debug("Locked seats for Screening: {}", saved);
        return saved.getSeats().screeningSeats()
                .stream()
                .filter(seat -> seatIds.contains(seat.getSeatId()))
                .collect(Collectors.toSet());
    }

    private boolean overlappingScreeningExistsInScreeningRoom(Screening screening) {
        return repository.findAllByScreeningRoomId(screening.getScreeningRoomId()).stream()
                .anyMatch(s -> s.getTime().overlaps(screening.getTime()));
    }

}
