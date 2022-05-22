package com.ramzes.notificationsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class NotificationSenderApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationSenderApplication.class, args);
    }
}
