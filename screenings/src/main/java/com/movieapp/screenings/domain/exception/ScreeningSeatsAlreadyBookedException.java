package com.movieapp.screenings.domain.exception;

import com.movieapp.screenings.domain.model.SeatId;

import java.util.List;
import java.util.Set;

public class ScreeningSeatsAlreadyBookedException extends Exception {
    private final List<SeatId> alreadyReservedIds;

    public ScreeningSeatsAlreadyBookedException(List<SeatId> alreadyReservedIds) {
        this.alreadyReservedIds = alreadyReservedIds;
    }

    public List<SeatId> getAlreadyReservedIds() {
        return alreadyReservedIds;
    }

}
