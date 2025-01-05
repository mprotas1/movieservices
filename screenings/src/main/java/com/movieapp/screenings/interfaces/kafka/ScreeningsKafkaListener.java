package com.movieapp.screenings.interfaces.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.screenings.application.dto.ReservationDTO;
import com.movieapp.screenings.application.service.ScreeningApplicationService;
import com.movieapp.screenings.infrastructure.entity.ScreeningEntity;
import com.movieapp.screenings.infrastructure.entity.ScreeningSeatEntity;
import com.movieapp.screenings.infrastructure.persistence.JpaScreeningRepository;
import com.movieapp.screenings.infrastructure.persistence.JpaScreeningSeatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class ScreeningsKafkaListener {
    private final ObjectMapper objectMapper;
    private final ScreeningApplicationService screeningsService;

    @KafkaListener(topics = "reservation_created", groupId = "basic")
    void reservationCreatedListener(String serializedReservation) {
        log.info("Received reservation created event: {}", serializedReservation);
        ReservationDTO reservationDTO = mapReservationDTO(serializedReservation);
        log.info("Mapped reservation DTO: {}", reservationDTO);
        screeningsService.lockSeats(reservationDTO);
    }

    private ReservationDTO mapReservationDTO(String reservationDTO) {
        try {
            return objectMapper.readValue(reservationDTO, ReservationDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error while mapping reservation DTO: {}", e.getMessage());
            throw new RuntimeException("Error while mapping Reservation DTO", e);
        }
    }

}