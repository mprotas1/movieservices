package com.movieapp.screenings.infrastructure.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicsConfiguration {

    @Bean
    public NewTopic seatsAlreadyLockedTopic() {
        return TopicBuilder
                .name("screening_seats_already_reserved")
                .build();
    }

    @Bean
    public NewTopic screeningSeatsBookingFailedTopic() {
        return TopicBuilder
                .name("screening_seats_booking_failed")
                .build();
    }

    @Bean
    public NewTopic successfulSeatsLockTopic() {
        return TopicBuilder
                .name("successful_seats_booking")
                .build();
    }

}
