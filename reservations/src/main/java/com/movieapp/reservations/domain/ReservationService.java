package com.movieapp.reservations.domain;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.mapper.ReservationMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
class ReservationService implements ReservationDomainService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public Reservation makeReservation(ReservationCreateRequest reservationDTO) {
        log.debug("Making reservation: {}", reservationDTO);
        Reservation domain = reservationMapper.toDomain(reservationDTO);
        return reservationRepository.save(domain);
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

    @Override
    public Reservation bookReservation(ReservationId reservationId, ReservationPrice price) {
        log.debug("Booking reservation with id: {}", reservationId.getId());
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id: " + reservationId.getId() + " not found"));
        reservation.book(price);
        log.debug("Reservation booked: {}", reservation);
        return reservationRepository.save(reservation);
    }

}
