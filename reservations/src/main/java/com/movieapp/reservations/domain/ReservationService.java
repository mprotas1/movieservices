package com.movieapp.reservations.domain;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.mapper.ReservationMapper;
import com.movieapp.reservations.interfaces.client.ScreeningClient;
import com.movieapp.reservations.interfaces.client.SeatClient;
import com.movieapp.reservations.interfaces.client.UserClient;
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

    private void validateScreening(UUID screeningId) {
        if(screeningClient.getScreening(screeningId).isEmpty()) {
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
                    throw new SeatIsAlreadyTakenException("Seat is already reserved");
                });
    }

    private void checkIfSeatExists(UUID seatId) {
        if(seatClient.getSeat(seatId).isEmpty()) {
            throw new SeatNotFoundException("Seat not found");
        }
    }

    private void validateUser(Long userId) {
        if(userClient.getUser(userId).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
    }

}
