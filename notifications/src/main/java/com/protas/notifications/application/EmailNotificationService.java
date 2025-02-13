package com.protas.notifications.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class EmailNotificationService implements NotificationService {
    private final JavaMailSender mailSender;

    EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void handle(NotificationRequest notificationRequest) {
        // log.info("Sending email notification: {}", notificationRequest);
        mailSender.send(notificationRequest.toSimpleMailMessage("moviesapp@gmail.com"));
        // log.info("Email notification sent to user: {}", notificationRequest.email());
    }

}
