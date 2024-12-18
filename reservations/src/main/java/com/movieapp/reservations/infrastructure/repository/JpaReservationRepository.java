package com.movieapp.reservations.infrastructure.repository;

import com.movieapp.reservations.infrastructure.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    @Query("SELECT r FROM ReservationEntity r WHERE r.screeningId = :id")
    List<ReservationEntity> findByScreeningId(UUID id);
    @Query("SELECT r FROM ReservationEntity r WHERE r.userId = :id")
    List<ReservationEntity> findByUserId(Long id);
    @Query("SELECT r FROM ReservationEntity r WHERE r.screeningId = :screeningId AND r.seatId = :seatId")
    Optional<ReservationEntity> findByScreeningIdAndSeatId(UUID screeningId, UUID seatId);
}
