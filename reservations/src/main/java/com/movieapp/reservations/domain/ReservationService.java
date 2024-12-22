package com.movieapp.reservations.domain;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.mapper.ReservationMapper;
import com.movieapp.reservations.interfaces.client.ScreeningClient;
import com.movieapp.reservations.interfaces.client.SeatClient;
import com.movieapp.reservations.interfaces.client.UserClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
class ReservationService implements ReservationDomainService {
    private final ReservationRepository reservationRepository;
    private final SeatClient seatClient;
    private final ScreeningClient screeningClient;
    private final UserClient userClient;

    @Override
    @Transactional
    public Reservation makeReservation(ReservationCreateRequest reservationDTO) {
        log.debug("Making reservation: {}", reservationDTO);
        validateScreening(reservationDTO.screeningId());
        validateSeat(reservationDTO.seatId(), reservationDTO.screeningId());
        validateUser(reservationDTO.userId());
        return ReservationMapper.toDomain(reservationDTO);
    }

    @Override
    @Transactional
    public Reservation confirmReservation(ReservationId reservationId) {
        log.debug("Confirming reservation with id: {}", reservationId.getId());
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id: " + reservationId.getId() + " not found"));
        reservation.confirm();
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation cancelReservation(ReservationId reservationId) {
        log.debug("Cancelling reservation with id: {}", reservationId.getId());
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id: " + reservationId.getId() + " not found"));
        reservation.cancel();
        log.debug("Reservation cancelled: {}", reservation);
        return reservationRepository.save(reservation);
    }

    private void validateScreening(UUID screeningId) {
        if(screeningClient.getScreening(screeningId).isEmpty()) {
            log.debug("Screening by id: {} not found", screeningId);
            throw new ScreeningNotFoundException("Screening not found");
        }
    }

    private void validateSeat(UUID seatId, UUID screeningId) {
        checkIfSeatExists(seatId);
        checkIfSeatIsTakenForScreening(seatId, screeningId);
    }

    private void checkIfSeatIsTakenForScreening(UUID seatId, UUID screeningId) {
        reservationRepository.findByScreeningIdAndSeatId(new ScreeningId(screeningId), new SeatId(seatId))
                .ifPresent(_ -> {
                    log.debug("Seat with id: {} is already reserved for screening with id: {}", seatId, screeningId);
                    throw new SeatIsAlreadyTakenException("Seat is already reserved");
                });
    }

    private void checkIfSeatExists(UUID seatId) {
        if(seatClient.getSeat(seatId).isEmpty()) {
            log.debug("Seat with id: {} not found", seatId);
            throw new SeatNotFoundException("Seat not found");
        }
    }

    private void validateUser(Long userId) {
        if(userClient.getUser(userId).isEmpty()) {
            log.debug("User with id: {} not found", userId);
            throw new UserNotFoundException("User not found");
        }
    }

}
