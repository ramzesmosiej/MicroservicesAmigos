package com.ramzes.notificationsender;

import com.ramzes.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder().toCustomerId(notificationRequest.toCustomerId()).
                        toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender("Ramzes").message(notificationRequest.message()).sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
