package com.movieapp.reservations.domain;

import com.movieapp.model.ScreeningDTO;
import com.movieapp.model.SeatDTO;
import com.movieapp.model.UserDTO;
import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.interfaces.client.ScreeningClient;
import com.movieapp.reservations.interfaces.client.SeatClient;
import com.movieapp.reservations.interfaces.client.UserClient;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ScreeningClient screeningClient;
    @Mock
    private SeatClient seatClient;
    @Mock
    private UserClient userClient;

    @Test
    @DisplayName("Should make valid reservation")
    void shouldMakeReservation() {
        // given
        UUID screeningId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        Long userId = 1L;
        UUID screeningRoomId = UUID.randomUUID();
        ReservationCreateRequest request = new ReservationCreateRequest(screeningId, seatId, userId);

        ScreeningDTO screeningDTO = new ScreeningDTO(
                screeningId,
                1L,
                screeningRoomId,
                Instant.now().plus(1, ChronoUnit.MINUTES),
                Instant.now().plus(151, ChronoUnit.MINUTES),
                "Forest Gump"
        );

        SeatDTO seatDTO = new SeatDTO(
                seatId,
                screeningRoomId,
                1,
                1,
                1
        );

        UserDTO userDTO = new UserDTO(
                userId,
                "John",
                List.of("USER")
        );

        // when
        when(screeningClient.getScreening(screeningId)).thenReturn(Optional.of(screeningDTO));
        when(seatClient.getSeat(seatId)).thenReturn(Optional.of(seatDTO));
        when(reservationRepository.findByScreeningIdAndSeatId(new ScreeningId(screeningId), new SeatId(seatId))).thenReturn(Optional.empty());
        when(userClient.getUser(userId)).thenReturn(Optional.of(userDTO));

        // then
        Reservation reservation = reservationService.makeReservation(request);

        assertNotNull(reservation);
        assertNotNull(reservation.getReservationId());
        assertEquals(seatId, reservation.getSeatId().id());
        assertEquals(screeningId, reservation.getScreeningId().id());
        assertEquals(userId, reservation.getUserId().id());
    }

    @Test
    @DisplayName("Should throw exception when screening not found")
    void shouldThrowExceptionWhenScreeningNotFound() {
        // given
        UUID screeningId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        Long userId = 1L;
        ReservationCreateRequest request = new ReservationCreateRequest(screeningId, seatId, userId);

        // when
        when(screeningClient.getScreening(screeningId)).thenReturn(Optional.empty());

        // then
        assertThrows(ScreeningNotFoundException.class, () -> reservationService.makeReservation(request));
    }

    @Test
    @DisplayName("Should throw exception when seat not found")
    void shouldThrowExceptionWhenSeatNotFound() {
        // given
        UUID screeningId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        Long userId = 1L;
        ReservationCreateRequest request = new ReservationCreateRequest(screeningId, seatId, userId);

        ScreeningDTO screeningDTO = new ScreeningDTO(
                screeningId,
                1L,
                UUID.randomUUID(),
                Instant.now().plus(1, ChronoUnit.MINUTES),
                Instant.now().plus(151, ChronoUnit.MINUTES),
                "Forest Gump"
        );

        // when
        when(screeningClient.getScreening(screeningId)).thenReturn(Optional.of(screeningDTO));
        when(seatClient.getSeat(seatId)).thenReturn(Optional.empty());

        // then
        assertThrows(SeatNotFoundException.class, () -> reservationService.makeReservation(request));
    }

    @Test
    @DisplayName("Should throw exception when seat is already taken")
    void shouldThrowExceptionWhenSeatIsAlreadyTaken() {
        // given
        UUID screeningId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        Long userId = 1L;
        UUID screeningRoomId = UUID.randomUUID();
        ReservationCreateRequest request = new ReservationCreateRequest(screeningId, seatId, userId);

        ScreeningDTO screeningDTO = new ScreeningDTO(
                screeningId,
                1L,
                screeningRoomId,
                Instant.now().plus(1, ChronoUnit.MINUTES),
                Instant.now().plus(151, ChronoUnit.MINUTES),
                "Forest Gump"
        );

        SeatDTO seatDTO = new SeatDTO(
                seatId,
                screeningRoomId,
                1,
                1,
                1
        );

        // when
        when(screeningClient.getScreening(screeningId)).thenReturn(Optional.of(screeningDTO));
        when(seatClient.getSeat(seatId)).thenReturn(Optional.of(seatDTO));
        when(reservationRepository.findByScreeningIdAndSeatId(new ScreeningId(screeningId), new SeatId(seatId))).thenReturn(Optional.of(new Reservation(
                new ScreeningId(screeningId),
                new SeatId(seatId),
                new UserId(userId)
        )));

        // then
        assertThrows(SeatIsAlreadyTakenException.class, () -> reservationService.makeReservation(request));
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // given
        UUID screeningId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        Long userId = 1L;
        ReservationCreateRequest request = new ReservationCreateRequest(screeningId, seatId, userId);

        ScreeningDTO screeningDTO = new ScreeningDTO(
                screeningId,
                1L,
                UUID.randomUUID(),
                Instant.now().plus(1, ChronoUnit.MINUTES),
                Instant.now().plus(151, ChronoUnit.MINUTES),
                "Forest Gump"
        );

        SeatDTO seatDTO = new SeatDTO(
                seatId,
                UUID.randomUUID(),
                1,
                1,
                1
        );

        // when
        when(screeningClient.getScreening(screeningId)).thenReturn(Optional.of(screeningDTO));
        when(seatClient.getSeat(seatId)).thenReturn(Optional.of(seatDTO));
        when(reservationRepository.findByScreeningIdAndSeatId(new ScreeningId(screeningId), new SeatId(seatId))).thenReturn(Optional.empty());
        when(userClient.getUser(userId)).thenReturn(Optional.empty());

        // then
        assertThrows(UserNotFoundException.class, () -> reservationService.makeReservation(request));
    }

    @Test
    @DisplayName("Should confirm reservation")
    void shouldConfirmReservation() {
        // given
        UUID screeningId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        Long userId = 1L;
        Reservation reservation = new Reservation(
                new ScreeningId(screeningId),
                new SeatId(seatId),
                new UserId(userId)
        );

        // when
        when(reservationRepository.findById(reservation.getReservationId())).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // then
        Reservation confirmedReservation = reservationService.confirmReservation(reservation.getReservationId());
        assertEquals(ReservationStatus.CONFIRMED, confirmedReservation.getStatus());
    }

    @Test
    @DisplayName("Should not confirm non-existing reservation")
    void shouldNotConfirmNonExistingReservation() {
        // given
        var reservationId = new ReservationId(UUID.randomUUID());

        // when
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> reservationService.confirmReservation(reservationId));
    }

    @Test
    @DisplayName("Should not confirm cancelled reservation")
    void shouldNotConfirmCancelledReservation() {
        // given
        UUID screeningId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        Long userId = 1L;
        Reservation reservation = new Reservation(
                new ScreeningId(screeningId),
                new SeatId(seatId),
                new UserId(userId)
        );

        // when
        when(reservationRepository.findById(reservation.getReservationId())).thenReturn(Optional.of(reservation));

        // then
        reservationService.cancelReservation(reservation.getReservationId());
        assertThrows(InvalidReservationTransitionException.class, () -> reservationService.confirmReservation(reservation.getReservationId()));
    }

    @Test
    @DisplayName("Should cancel reservation")
    void shouldCancelReservation() {
        // given
        UUID screeningId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        Long userId = 1L;
        Reservation reservation = new Reservation(
                new ScreeningId(screeningId),
                new SeatId(seatId),
                new UserId(userId)
        );

        // when
        when(reservationRepository.findById(reservation.getReservationId())).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // then
        Reservation cancelledReservation = reservationService.cancelReservation(reservation.getReservationId());
        assertEquals(ReservationStatus.CANCELLED, cancelledReservation.getStatus());
    }


}
