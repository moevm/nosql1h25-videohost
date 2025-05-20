package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
public class VideoHostingApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoHostingApplication.class, args);
    }
}