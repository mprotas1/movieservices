package com.movieapp.screenings.interfaces.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.screenings.application.dto.SuccessfulSeatsBookingEvent;
import com.movieapp.screenings.application.events.NoSeatsToBookEvent;
import com.movieapp.screenings.application.events.ScreeningDoesNotExistEvent;
import com.movieapp.screenings.application.events.SeatsAlreadyLockedEvent;
import com.movieapp.screenings.domain.exception.ScreeningSeatsBookingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
class ScreeningsKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @EventListener
    void seatsAlreadyReserved(SeatsAlreadyLockedEvent lockedSeatsEvent) {
        String topic = "screening_seats_already_reserved";
        log.debug("Sending the seats already reserved event to Kafka topic: {}", topic);
        kafkaTemplate.send(topic, mapEvent(lockedSeatsEvent));
    }

    @EventListener
    void successfulSeatsBooking(SuccessfulSeatsBookingEvent successfulSeatsBookingEvent) {
        String topic = "successful_seats_booking";
        log.debug("Sending the successful reservation seats booked event to Kafka topic: {}", topic);
        kafkaTemplate.send(topic, mapEvent(successfulSeatsBookingEvent));
    }

    @EventListener
    void onSeatsBookingFailed(ScreeningDoesNotExistEvent event) {
        String topic = "screening_seats_booking_failed";
        log.debug("Sending the screening does not exist event to Kafka topic: {}", topic);
        kafkaTemplate.send(topic, mapEvent(event));
    }

    @EventListener
    void noSeatsToBook(NoSeatsToBookEvent noSeatsToBookEvent) {
        String topic = "no_seats_to_book";
        log.debug("Sending the no seats to book event to Kafka topic: {}", topic);
        kafkaTemplate.send(topic, mapEvent(noSeatsToBookEvent));
    }

    private String mapEvent(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Error while mapping seats already reserved event: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
