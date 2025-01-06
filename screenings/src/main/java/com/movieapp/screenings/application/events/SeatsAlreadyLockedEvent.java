package com.movieapp.screenings.application.events;

import com.movieapp.screenings.domain.model.SeatId;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SeatsAlreadyLockedEvent {
    private final UUID reservationId;
    private final List<UUID> screeningSeatIds;

    public SeatsAlreadyLockedEvent(UUID reservationId, Collection<SeatId> screeningSeatIds) {
        this.reservationId = reservationId;
        this.screeningSeatIds = screeningSeatIds.stream()
                .map(SeatId::id)
                .toList();
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public List<UUID> getScreeningSeatIds() {
        return screeningSeatIds;
    }

}


