package com.ramzes.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("fraud")
public interface FraudClient {

    @GetMapping(value = "api/v1/fraud-check/{customerId}")
    FraudCheckResponse isFraudulentCustomer(@PathVariable("customerId") Integer customerId);
}
