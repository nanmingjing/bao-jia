package com.example.aaacar2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsurerPolicy {


    private String insurer;

    private String brand;

    private String model;

    private String level;

    private String region;

    @JsonProperty("plan_tier")
    private String planTier;

    @JsonProperty("premium_min")
    private Double premiumMin;

    @JsonProperty("premium_max")
    private Double premiumMax;

    @JsonProperty("commission_rate")
    private Double commissionRate;

    @JsonProperty("pass_rate")
    private Double passRate;


    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }

}
