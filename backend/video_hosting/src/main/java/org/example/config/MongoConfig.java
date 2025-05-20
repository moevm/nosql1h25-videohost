package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "moscowDateTimeProvider")
public class MongoConfig {

    @Bean
    public DateTimeProvider moscowDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now(ZoneId.of("Europe/Moscow")));
    }
}
