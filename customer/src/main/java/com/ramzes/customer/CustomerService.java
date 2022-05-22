package com.ramzes.customer;

import com.ramzes.clients.fraud.FraudCheckResponse;
import com.ramzes.clients.fraud.FraudClient;
import com.ramzes.clients.notification.NotificationClient;
import com.ramzes.clients.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;


@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate,
                              FraudClient fraudClient, NotificationClient notificationClient) {
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
                customer.getEmail(), "Welcome" + customer.getFirstName() + "in the course.");
        notificationClient.sendNotification(notificationRequest);

    }

    public void encourageToBuy(CustomerRegistrationRequest customerRegistrationRequest) {
        try {
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();

            synthesizer.resume();

            synthesizer.speakPlainText(
                    "Siema" + customerRegistrationRequest.firstName() + customerRegistrationRequest.lastName() +
                            "give me your money" + customerRegistrationRequest.email() +
                            "" + "", null);
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);

            synthesizer.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
