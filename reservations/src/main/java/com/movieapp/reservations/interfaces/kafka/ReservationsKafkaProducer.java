package com.movieapp.reservations.interfaces.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.reservations.application.dto.ReservationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class ReservationsKafkaProducer implements KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    ReservationsKafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void reservationCreated(ReservationDTO reservationDTO) {
        String topic = "reservation_created";
        log.debug("Sending the reservation created event to Kafka topic: {} with the following data: {}", topic, reservationDTO);
        String jsonReservation = getJsonReservation(reservationDTO);
        kafkaTemplate.send(topic, jsonReservation);
        log.debug("Reservation created event sent");
    }

    private String getJsonReservation(ReservationDTO reservationDTO) {
        try {
            return objectMapper.writeValueAsString(reservationDTO);
        } catch (Exception e) {
            log.error("Error serializing the reservation DTO: {}", reservationDTO, e);
            throw new RuntimeException("Error serializing the reservation DTO", e);
        }
    }

}
