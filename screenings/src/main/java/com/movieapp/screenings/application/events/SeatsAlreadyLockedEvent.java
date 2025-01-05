package com.movieapp.screenings.application.events;

import java.util.List;
import java.util.UUID;

public record SeatsAlreadyLockedEvent(UUID reservationId, List<UUID> alreadyReservedSeatIds) {}


