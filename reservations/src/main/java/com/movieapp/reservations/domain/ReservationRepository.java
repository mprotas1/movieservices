package com.movieapp.reservations.domain;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(ReservationId id);
    Optional<Reservation> findByScreeningIdAndSeatId(ScreeningId screeningId, SeatId seatId);
    List<Reservation> findAll();
    List<Reservation> findByScreeningId(ScreeningId screeningId);
    List<Reservation> findByUserId(UserId userId);
    void delete(Reservation reservation);
    void deleteById(ReservationId id);
}