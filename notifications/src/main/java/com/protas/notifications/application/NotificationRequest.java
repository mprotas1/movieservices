package com.protas.notifications.application;

import org.springframework.mail.SimpleMailMessage;

public record NotificationRequest(
        String email,
        String subject,
        String message
) {

    public SimpleMailMessage toSimpleMailMessage(String from) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email());
        message.setSubject(subject());
        message.setText(message());
        return message;
    }

}
