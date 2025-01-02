package com.movieapp.screenings.infrastructure.persistence;

import com.movieapp.screenings.infrastructure.entity.ScreeningSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaScreeningSeatRepository extends JpaRepository<ScreeningSeatEntity, UUID> {}
