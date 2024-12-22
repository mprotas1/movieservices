package com.movieapp.reservations.infrastructure.repository;

import com.movieapp.reservations.application.mapper.ReservationMapper;
import com.movieapp.reservations.domain.*;
import com.movieapp.reservations.infrastructure.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
class Reservations implements ReservationRepository {
    private final JpaReservationRepository jpaReservationRepository;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity reservationEntity = ReservationMapper.toEntity(reservation);
        ReservationEntity savedReservationEntity = jpaReservationRepository.save(reservationEntity);
        return ReservationMapper.toDomain(savedReservationEntity);
    }

    @Override
    public Optional<Reservation> findById(ReservationId reservationId) {
        Optional<ReservationEntity> entityById = jpaReservationRepository.findById(reservationId.getId());
        return entityById.map(ReservationMapper::toDomain);
    }

    @Override
    public Optional<Reservation> findByScreeningIdAndSeatId(ScreeningId screeningId, SeatId seatId) {
        return jpaReservationRepository.findByScreeningIdAndSeatId(screeningId.id(), seatId.id())
                .map(ReservationMapper::toDomain);
    }

    @Override
    public List<Reservation> findAll() {
        return jpaReservationRepository.findAll().stream()
                .map(ReservationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Reservation> findByScreeningId(ScreeningId screeningId) {
        return jpaReservationRepository.findByScreeningId(screeningId.id()).stream()
                .map(ReservationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Reservation> findByUserId(UserId userId) {
        return jpaReservationRepository.findByUserId(userId.id()).stream()
                .map(ReservationMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Reservation reservation) {
        jpaReservationRepository.delete(ReservationMapper.toEntity(reservation));
    }

    @Override
    public void deleteById(ReservationId reservationId) {
        jpaReservationRepository.deleteById(reservationId.getId());
    }

}
