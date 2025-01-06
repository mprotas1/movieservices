package com.movieapp.screenings.domain.exception;

import com.movieapp.screenings.domain.model.SeatId;

import java.util.Set;

public class ScreeningSeatsAlreadyBookedException extends Exception {
    private final Set<SeatId> alreadyReservedIds;

    public ScreeningSeatsAlreadyBookedException(Set<SeatId> alreadyReservedIds) {
        this.alreadyReservedIds = alreadyReservedIds;
    }

    public Set<SeatId> getAlreadyReservedIds() {
        return alreadyReservedIds;
    }

}
