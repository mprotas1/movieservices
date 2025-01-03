package com.movieapp.reservations.application.service;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.dto.ReservationDTO;
import com.movieapp.reservations.application.mapper.ReservationMapper;
import com.movieapp.reservations.domain.*;
import com.movieapp.reservations.interfaces.kafka.KafkaProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class ReservationAppService implements ReservationApplicationService {
    private final ReservationDomainService reservationDomainService;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final KafkaProducer kafkaProducer;

    @Override
    public ReservationDTO makeReservation(ReservationCreateRequest request) {
        log.debug("Making reservation for request: {}", request);
        Reservation createdReservation = reservationDomainService.makeReservation(request);
        log.debug("Reservation created: {}", createdReservation);
        Reservation saved = reservationRepository.save(createdReservation);
        ReservationDTO dto = reservationMapper.toDTO(saved);
        kafkaProducer.reservationCreated(dto);
        return dto;
    }

    @Override
    public ReservationDTO confirmReservation(ReservationId reservationId) {
        log.debug("Confirming reservation with id: {}", reservationId.getId());
        Reservation reservation = reservationDomainService.confirmReservation(reservationId);
        return reservationMapper.toDTO(reservation);
    }

    @Override
    public ReservationDTO cancelReservation(ReservationId reservationId) {
        log.debug("Cancelling reservation with id: {}", reservationId.getId());
        Reservation reservation = reservationDomainService.cancelReservation(reservationId);
        return reservationMapper.toDTO(reservation);
    }

    @Override
    public ReservationDTO findById(ReservationId reservationId) {
        return reservationRepository.findById(reservationId)
                .map(reservationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id: " + reservationId.getId() + " not found"));
    }

    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    @Override
    public List<ReservationDTO> findAllByScreeningId(ScreeningId screeningId) {
        return reservationRepository.findByScreeningId(screeningId).stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    @Override
    public List<ReservationDTO> findUserReservations(UserId userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteById(ReservationId reservationId) {
        log.debug("Deleting reservation with id: {}", reservationId.getId());
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Reservation with id: " + reservationId.getId() + " not found")
                );
        reservationRepository.delete(reservation);
    }

}
