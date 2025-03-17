package com.protas.notifications.application;

public record NotificationRequest(
        String email,
        String subject,
        String message
) {}
