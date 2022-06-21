package com.ramzes.customer;

import com.ramzes.amqp.RabbitMQMessageProducer;
import com.ramzes.clients.fraud.FraudCheckResponse;
import com.ramzes.clients.fraud.FraudClient;
import com.ramzes.clients.notification.NotificationClient;
import com.ramzes.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder().firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudulentCustomer(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(),
                customer.getEmail(), "Welcome " + customer.getFirstName() + " in the course.");

        rabbitMQMessageProducer.publish(
                notificationRequest, "internal.exchange", "internal.notification.routing-key"
        );
    }

}
