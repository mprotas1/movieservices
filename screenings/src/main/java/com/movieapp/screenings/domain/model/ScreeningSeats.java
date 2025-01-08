package com.movieapp.screenings.domain.model;

import com.movieapp.screenings.domain.exception.ScreeningSeatsAlreadyBookedException;
import com.movieapp.screenings.domain.exception.ScreeningSeatsBookingException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ScreeningSeats(Set<ScreeningSeat> screeningSeats) {

    public void lockSeats(List<SeatId> seatIds) throws ScreeningSeatsAlreadyBookedException, ScreeningSeatsBookingException {
        validateScreeningSeatsState(seatIds);
        lockValidSeats(seatIds);
    }

    private void validateScreeningSeatsState(List<SeatId> seatIds) throws ScreeningSeatsAlreadyBookedException, ScreeningSeatsBookingException {
        Set<ScreeningSeat> screeningSeats = screeningSeats().stream()
                .filter(screeningSeat -> seatIds.contains(screeningSeat.getSeatId()))
                .collect(Collectors.toSet());

        if(screeningSeats.isEmpty()) {
            throw new ScreeningSeatsBookingException("No valid seats to book for Screening");
        }

        List<SeatId> alreadyReservedSeatIds = getAlreadyReservedSeats();
        if (!alreadyReservedSeatIds.isEmpty()) {
            throw new ScreeningSeatsAlreadyBookedException(alreadyReservedSeatIds);
        }
    }

    private List<SeatId> getAlreadyReservedSeats() {
        return screeningSeats.stream()
                .filter(ScreeningSeat::isReserved)
                .map(ScreeningSeat::getSeatId)
                .toList();
    }

    private void lockValidSeats(List<SeatId> seatIds) {
        screeningSeats.stream()
                .filter(screeningSeat -> seatIds.contains(screeningSeat.getSeatId()))
                .forEach(screeningSeat -> screeningSeat.setReserved(true));
    }

}
