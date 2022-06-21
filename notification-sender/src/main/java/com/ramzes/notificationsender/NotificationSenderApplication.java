package com.ramzes.notificationsender;

import com.ramzes.amqp.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication(
        scanBasePackages = {
                "com.ramzes.notificationsender",
                "com.ramzes.amqp"
        }
)
public class NotificationSenderApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationSenderApplication.class, args);
    }
}

/*    @Bean
    CommandLineRunner commandLineRunner(RabbitMQMessageProducer producer, NotificationConfig notificationConfig) {
        return args -> {
            producer.publish(new Person("Ali", 18), notificationConfig.getInternalExchange(), notificationConfig.getInternalNotificationRoutingKey());
        };
    }

    record Person(String name, int age){}
}*/
