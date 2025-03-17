package com.protas.notifications.infrastructure.configuration;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MailConfiguration {

    @Bean
    SendGrid sendGrid(@Value("${mailing.api-key}") String apiKey) {
        return new SendGrid(apiKey);
    }

}
