package com.movieapp.screenings.interfaces.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.screenings.application.dto.ReservationDTO;
import com.movieapp.screenings.infrastructure.entity.ScreeningSeatEntity;
import com.movieapp.screenings.infrastructure.persistence.JpaScreeningSeatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class ReservationKafkaListener {
    private final ObjectMapper objectMapper;
    private final JpaScreeningSeatRepository jpaScreeningSeatRepository;

    public ReservationKafkaListener(ObjectMapper objectMapper, JpaScreeningSeatRepository jpaScreeningSeatRepository) {
        this.objectMapper = objectMapper;
        this.jpaScreeningSeatRepository = jpaScreeningSeatRepository;
    }

    @KafkaListener(topics = "reservation_created", groupId = "basic")
    void reservationCreatedListener(String reservationDTO) {
        log.info("Received reservation created event: {}", reservationDTO);
        try {
            ReservationDTO mappedDTO = objectMapper.readValue(reservationDTO, ReservationDTO.class);
            log.info("Mapped reservation DTO: {}", mappedDTO);

            for(UUID seatId : mappedDTO.seatIds()) {
                Optional<ScreeningSeatEntity> byId = jpaScreeningSeatRepository.findById(seatId);
                if(byId.isEmpty()) {
                    continue;
                }
                ScreeningSeatEntity screeningSeatEntity = byId.get();
                if(screeningSeatEntity.isReserved()) {
                    log.error("Seat with id {} is already reserved", seatId);
                }
                log.debug("Reserving seat with id {}", seatId);
                screeningSeatEntity.setReserved(true);
                jpaScreeningSeatRepository.save(screeningSeatEntity);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
