package com.protas.notifications.application;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class EmailNotificationService implements NotificationService {
    private final SendGrid sendGrid;

    @Override
    public void handle(NotificationRequest notificationRequest) {
        log.info("Sending email notification: {}", notificationRequest);
        Email from = new Email("michalprotas00@gmail.com");
        Email to = new Email(notificationRequest.email());
        Content content = new Content("text/plain", notificationRequest.message());
        Mail mail = new Mail(from, notificationRequest.subject(), to, content);


        try {
            Request request = new Request();
            request.setEndpoint("mail/send");
            request.setMethod(Method.POST);
            request.setBody(mail.build());
            request.addHeader("Content-Type", "application/json");
            sendGrid.api(request);
        } catch (Exception e) {
            log.error("Error while sending email notification: {}", e.getMessage());
            throw new RuntimeException("Error while sending email notification", e);
        }

        log.info("Email notification sent to user: {}", notificationRequest.email());
    }

}
