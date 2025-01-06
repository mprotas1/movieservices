package com.movieapp.screenings.domain.model;

import com.movieapp.screenings.domain.exception.ScreeningSeatsAlreadyBookedException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ScreeningSeats(Set<ScreeningSeat> screeningSeats) {

    public void lockSeats(List<SeatId> seatIds) throws ScreeningSeatsAlreadyBookedException {
        validateScreeningSeatsState(seatIds);
        lockValidSeats(seatIds);
    }

    private void validateScreeningSeatsState(List<SeatId> seatIds) throws ScreeningSeatsAlreadyBookedException {
        Set<SeatId> alreadyReserved = new HashSet<>();
        Set<ScreeningSeat> screeningSeats = screeningSeats().stream()
                .filter(screeningSeat -> seatIds.contains(screeningSeat.getSeatId()))
                .collect(Collectors.toSet());
        for(ScreeningSeat screeningSeat : screeningSeats) {
            if (screeningSeat.isReserved()) {
                alreadyReserved.add(screeningSeat.getSeatId());
            }
        }
        if (!alreadyReserved.isEmpty()) {
            throw new ScreeningSeatsAlreadyBookedException(alreadyReserved);
        }
    }

    private void lockValidSeats(List<SeatId> seatIds) {
        screeningSeats.stream()
                .filter(screeningSeat -> seatIds.contains(screeningSeat.getSeatId()))
                .forEach(screeningSeat -> screeningSeat.setReserved(true));
    }

}
