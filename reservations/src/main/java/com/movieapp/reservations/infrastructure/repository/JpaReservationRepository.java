package com.movieapp.reservations.infrastructure.repository;

import com.movieapp.reservations.infrastructure.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByScreeningId(UUID screeningId);
    List<ReservationEntity> findByUserId(Long userId);
    Optional<ReservationEntity> findByScreeningIdAndSeatId(UUID screeningId, UUID seatId);
}