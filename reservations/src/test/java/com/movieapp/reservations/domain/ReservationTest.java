package com.movieapp.reservations.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationTest {

    @Test
    @DisplayName("Domain object Reservation should be created with pending status")
    void shouldCreateReservationWithPendingStatus() {
        var screeningId = new ScreeningId(UUID.randomUUID());
        var seatId = new SeatId(UUID.randomUUID());
        var userId = new UserId(1L);

        Reservation reservation = new Reservation(
            screeningId,
                List.of(new SeatId(UUID.randomUUID())),
            userId
        );

        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        assertEquals(screeningId, reservation.getScreeningId());
        assertEquals(userId, reservation.getUserId());
    }

    @Test
    @DisplayName("Domain object should be cancelled")
    void shouldCancelReservation() {
        var screeningId = new ScreeningId(UUID.randomUUID());
        var seatId = new SeatId(UUID.randomUUID());
        var userId = new UserId(1L);

        Reservation reservation = new Reservation(
            screeningId,
                List.of(new SeatId(UUID.randomUUID())),
            userId
        );

        reservation.cancel();
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }

    @Test
    @DisplayName("Domain object should be confirmed")
    void shouldConfirmReservation() {
        var screeningId = new ScreeningId(UUID.randomUUID());
        var userId = new UserId(1L);

        Reservation reservation = new Reservation(
            screeningId,
                List.of(new SeatId(UUID.randomUUID())),
            userId
        );

        reservation.confirm();
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
    }

    @Test
    @DisplayName("Domain object should not be confirmed when is already cancelled")
    void shouldNotConfirmCancelledReservation() {
        var screeningId = new ScreeningId(UUID.randomUUID());
        var seatId = new SeatId(UUID.randomUUID());
        var userId = new UserId(1L);

        Reservation reservation = new Reservation(
            screeningId,
                List.of(new SeatId(UUID.randomUUID())),
            userId
        );

        reservation.cancel();
        assertThrows(InvalidReservationTransitionException.class, reservation::confirm);
    }

}
