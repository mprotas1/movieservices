package com.movieapp.reservations.infrastructure.repository;

import com.movieapp.reservations.domain.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<ReservationId, Reservation> reservations = new HashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(ReservationId id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public List<Reservation> findByScreeningId(ScreeningId screeningId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getScreeningId().equals(screeningId))
                .toList();
    }

    @Override
    public List<Reservation> findByUserId(UserId userId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getUserId().equals(userId))
                .toList();
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation.getReservationId());
    }

    @Override
    public void deleteById(ReservationId id) {
        reservations.remove(id);
    }
}
