package com.example.aaacar2.rule;

import com.example.aaacar2.service.PolicyService;
import org.springframework.stereotype.Component;

@Component
public class QuoteRuleEngine {

    private final PolicyService policyService;

    public QuoteRuleEngine(PolicyService policyService) {
        this.policyService = policyService;
    }

    public double calculate(String brand, String region, String insurer) {
        double basePrice = policyService.getBasePrice();
        double brandFactor = policyService.getBrandFactor(brand);
        double regionFactor = policyService.getRegionFactor(region);
        double discount = policyService.getInsurerDiscount(insurer);

        return basePrice * brandFactor * regionFactor * discount;
    }
}