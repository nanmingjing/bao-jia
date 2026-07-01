package com.example.aaacar2.dto;

import java.util.HashMap;
import java.util.Map;

public class QuoteRequest {

    private String brand;
    private String model;
    private String region;
    private String insurer;

    // 地区额外字段（车身颜色、发动机号等）
    private Map<String, Object> extraFields = new HashMap<>();

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public Map<String, Object> getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(Map<String, Object> extraFields) {
        this.extraFields = extraFields;
    }

    public void putExtraField(String key, Object value) {
        this.extraFields.put(key, value);
    }

    public Object getExtraField(String key) {
        return this.extraFields.get(key);
    }
}