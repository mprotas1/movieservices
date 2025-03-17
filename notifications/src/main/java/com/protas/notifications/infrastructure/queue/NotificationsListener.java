package com.protas.notifications.infrastructure.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.protas.notifications.application.NotificationRequest;
import com.protas.notifications.application.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class NotificationsListener {
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    NotificationsListener(NotificationService notificationService, ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "send_notification")
    public void handleNotificationRequest(String notificationRequest) throws JsonProcessingException {
        NotificationRequest request = objectMapper.readValue(notificationRequest, NotificationRequest.class);
        log.info("Received notification request from Kafka queue: {}", request);
        notificationService.handle(request);
    }

}
