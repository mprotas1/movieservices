package com.movieapp.reservations.infrastructure.repository;

import com.movieapp.reservations.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryReservationRepositoryTest {
    private final ReservationRepository inMemoryReservationRepository = new InMemoryReservationRepository();

    @Test
    void shouldSaveReservation() {
        Reservation reservation = new Reservation(
                new ScreeningId(UUID.randomUUID()),
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        inMemoryReservationRepository.save(reservation);
        var reservationById = inMemoryReservationRepository.findById(reservation.getReservationId());
        assertTrue(reservationById.isPresent());
    }

    @Test
    void shouldFindReservationById() {
        Reservation reservation = new Reservation(
                new ScreeningId(UUID.randomUUID()),
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        inMemoryReservationRepository.save(reservation);
        var reservationById = inMemoryReservationRepository.findById(reservation.getReservationId());
        assertTrue(reservationById.isPresent());
    }

    @Test
    void shouldNotFindReservationById() {
        Reservation reservation = new Reservation(
                new ScreeningId(UUID.randomUUID()),
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        inMemoryReservationRepository.save(reservation);
        var reservationById = inMemoryReservationRepository.findById(new ReservationId(UUID.randomUUID()));
        assertTrue(reservationById.isEmpty());
    }

    @Test
    void shouldFindReservationByScreeningIdAndSeatId() {
        SeatId seatId = new SeatId(UUID.randomUUID());
        ScreeningId screeningId = new ScreeningId(UUID.randomUUID());

        Reservation reservation = new Reservation(
                screeningId,
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        inMemoryReservationRepository.save(reservation);
        var reservationByScreeningIdAndSeatId = inMemoryReservationRepository.findByScreeningId(screeningId);
        assertFalse(reservationByScreeningIdAndSeatId.isEmpty());
    }

    @Test
    void shouldNotFindReservationByScreeningIdAndSeatId() {
        SeatId seatId = new SeatId(UUID.randomUUID());
        ScreeningId screeningId = new ScreeningId(UUID.randomUUID());

        Reservation reservation = new Reservation(
                screeningId,
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        inMemoryReservationRepository.save(reservation);
        var reservationByScreeningIdAndSeatId = inMemoryReservationRepository.findByScreeningId(new ScreeningId(UUID.randomUUID()));
        assertTrue(reservationByScreeningIdAndSeatId.isEmpty());
    }

    @Test
    void shouldFindAllReservations() {
        Reservation reservation1 = new Reservation(
                new ScreeningId(UUID.randomUUID()),
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        Reservation reservation2 = new Reservation(
                new ScreeningId(UUID.randomUUID()),
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        inMemoryReservationRepository.save(reservation1);
        inMemoryReservationRepository.save(reservation2);
        var reservations = inMemoryReservationRepository.findAll();
        assertEquals(2, reservations.size());
    }

    @Test
    void shouldFindReservationsByScreeningId() {
        ScreeningId screeningId = new ScreeningId(UUID.randomUUID());

        Reservation reservation1 = new Reservation(
                screeningId,
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        Reservation reservation2 = new Reservation(
                screeningId,
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        inMemoryReservationRepository.save(reservation1);
        inMemoryReservationRepository.save(reservation2);
        var reservations = inMemoryReservationRepository.findByScreeningId(screeningId);
        assertEquals(2, reservations.size());
    }

    @Test
    void shouldFindReservationsByUserId() {
        UserId userId = new UserId(1L);

        Reservation reservation1 = new Reservation(
                new ScreeningId(UUID.randomUUID()),
                List.of(new SeatId(UUID.randomUUID())),
                userId
        );

        Reservation reservation2 = new Reservation(
                new ScreeningId(UUID.randomUUID()),
                List.of(new SeatId(UUID.randomUUID())),
                userId
        );

        inMemoryReservationRepository.save(reservation1);
        inMemoryReservationRepository.save(reservation2);
        var reservations = inMemoryReservationRepository.findByUserId(userId);
        assertEquals(2, reservations.size());
    }

    @Test
    void shouldDeleteReservation() {
        Reservation reservation = new Reservation(
                new ScreeningId(UUID.randomUUID()),
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        inMemoryReservationRepository.save(reservation);
        inMemoryReservationRepository.delete(reservation);
        var reservationById = inMemoryReservationRepository.findById(reservation.getReservationId());
        assertTrue(reservationById.isEmpty());
    }

    @Test
    void shouldDeleteReservationById() {
        Reservation reservation = new Reservation(
                new ScreeningId(UUID.randomUUID()),
                List.of(new SeatId(UUID.randomUUID())),
                new UserId(1L)
        );

        inMemoryReservationRepository.save(reservation);
        inMemoryReservationRepository.deleteById(reservation.getReservationId());
        var reservationById = inMemoryReservationRepository.findById(reservation.getReservationId());
        assertTrue(reservationById.isEmpty());
    }

}
