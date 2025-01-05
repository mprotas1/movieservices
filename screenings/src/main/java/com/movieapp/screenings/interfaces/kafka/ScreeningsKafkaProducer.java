package com.movieapp.screenings.interfaces.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.screenings.application.dto.ScreeningSeatsAlreadyLockedDTO;
import com.movieapp.screenings.application.events.SeatsAlreadyLockedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ScreeningsKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @EventListener
    public void seatsAlreadyReserved(SeatsAlreadyLockedEvent lockedSeatsEvent) {
        String topic = "screening_seats_reserved";
        log.debug("Sending the seats already reserved event to Kafka topic: {}", topic);
        ScreeningSeatsAlreadyLockedDTO screeningSeatsAlreadyLockedDTO = new ScreeningSeatsAlreadyLockedDTO(lockedSeatsEvent.reservationId(), lockedSeatsEvent.alreadyReservedSeatIds());
        kafkaTemplate.send(topic, mapSeatsAlreadyLockedDTO(screeningSeatsAlreadyLockedDTO));
    }

    private String mapSeatsAlreadyLockedDTO(ScreeningSeatsAlreadyLockedDTO responseDTO) {
        try {
            return objectMapper.writeValueAsString(responseDTO);
        } catch (JsonProcessingException e) {
            log.error("Error while mapping seats already reserved event: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
