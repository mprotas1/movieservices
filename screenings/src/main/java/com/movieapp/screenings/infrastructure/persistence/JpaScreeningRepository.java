package com.movieapp.screenings.infrastructure.persistence;

import com.movieapp.screenings.infrastructure.entity.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaScreeningRepository extends JpaRepository<ScreeningEntity, UUID> {
    List<ScreeningEntity> findAllByMovieId(Long movieId);
    List<ScreeningEntity> findAllByScreeningRoomId(UUID screeningRoomId);
}
