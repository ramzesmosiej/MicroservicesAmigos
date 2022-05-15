package com.ramzes.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@Slf4j
public class FraudController {

    private final FraudCheckHistoryService fraudCheckHistoryService;

    public FraudController(FraudCheckHistoryService fraudCheckHistoryService) {
        this.fraudCheckHistoryService = fraudCheckHistoryService;
    }

    @GetMapping("/{customerId}")
    public FraudCheckResponse isFraudulentCustomer(@PathVariable Integer customerId) {
         boolean isFraudulentCustomer = fraudCheckHistoryService.isFraudulentCustomer(customerId);
         log.info("fraud check request for customer {}", customerId);
         return new FraudCheckResponse(isFraudulentCustomer);
    }
}
