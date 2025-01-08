package com.movieapp.reservations.interfaces.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.reservations.application.events.ReservationCreatedEvent;
import com.movieapp.reservations.application.events.ReservationPaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
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
    @EventListener(ReservationCreatedEvent.class)
    public void reservationCreated(ReservationCreatedEvent event) {
        String topic = "reservation_created";
        log.debug("Sending the reservation created event to Kafka topic: {} with the following data: {}", topic, event.dto());
        String jsonReservation = serializeJsonEvent(event.dto());
        kafkaTemplate.send(topic, jsonReservation);
        log.debug("Reservation created event sent");
    }

    @Override
    @EventListener(ReservationPaymentEvent.class)
    public void askForPayment(ReservationPaymentEvent event) {
        String topic = "reservation_payment";
        log.debug("Sending the reservation payment event to Kafka topic: {} with the following data: {}", topic, event);
        String jsonReservation = serializeJsonEvent(event);
        kafkaTemplate.send(topic, jsonReservation);
    }

    private String serializeJsonEvent(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            log.error("Error serializing the reservation DTO: {}", event, e);
            throw new RuntimeException("Error serializing the reservation DTO", e);
        }
    }

}
