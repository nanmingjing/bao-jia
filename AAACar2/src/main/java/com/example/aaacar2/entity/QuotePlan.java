package com.example.aaacar2.entity;

public class QuotePlan {

    private String insurerId;

    private String planTier;

    private Double premiumMin;

    private Double premiumMax;

    private Double commission;

    private Double passRate;

    public String getInsurerId() {
        return insurerId;
    }

    public void setInsurerId(String insurerId) {
        this.insurerId = insurerId;
    }

    public String getPlanTier() {
        return planTier;
    }

    public void setPlanTier(String planTier) {
        this.planTier = planTier;
    }

    public Double getPremiumMin() {
        return premiumMin;
    }

    public void setPremiumMin(Double premiumMin) {
        this.premiumMin = premiumMin;
    }

    public Double getPremiumMax() {
        return premiumMax;
    }

    public void setPremiumMax(Double premiumMax) {
        this.premiumMax = premiumMax;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }
}