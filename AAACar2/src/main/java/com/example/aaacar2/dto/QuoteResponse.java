package com.example.aaacar2.dto;

import com.example.aaacar2.entity.InsurerGroupResponse;

import java.util.ArrayList;
import java.util.List;

public class QuoteResponse {

    private List<InsurerGroupResponse> plans;
    private boolean valid = true;
    private List<String> missingFields = new ArrayList<>();
    private String regionRuleMessage;
    private Double regionCoefficient;

    public List<InsurerGroupResponse> getPlans() {
        return plans;
    }

    public void setPlans(List<InsurerGroupResponse> plans) {
        this.plans = plans;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }

    public void setMissingFields(List<String> missingFields) {
        this.missingFields = missingFields;
    }

    public String getRegionRuleMessage() {
        return regionRuleMessage;
    }

    public void setRegionRuleMessage(String regionRuleMessage) {
        this.regionRuleMessage = regionRuleMessage;
    }

    public Double getRegionCoefficient() {
        return regionCoefficient;
    }

    public void setRegionCoefficient(Double regionCoefficient) {
        this.regionCoefficient = regionCoefficient;
    }
}