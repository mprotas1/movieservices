package com.movieapp.reservations.infrastructure.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class ReservationsTopicConfig {

    @Bean
    public NewTopic reservationsTopic() {
        return TopicBuilder.name("reservation_created")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic reservationPaymentTopic() {
        return TopicBuilder.name("reservation_payment")
                .partitions(5)
                .build();
    }

}
