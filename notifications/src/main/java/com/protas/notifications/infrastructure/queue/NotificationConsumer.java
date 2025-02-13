package com.protas.notifications.infrastructure.queue;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationConsumer {
    void handleNotificationRequest(String notificationRequest) throws JsonProcessingException;
}
