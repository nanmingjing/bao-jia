package com.example.aaacar2.entity;

import java.util.List;

public class InsurerGroupResponse {

    private String insurer;

    private List<InsurerPolicy> plans;

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public List<InsurerPolicy> getPlans() {
        return plans;
    }

    public void setPlans(List<InsurerPolicy> plans) {
        this.plans = plans;
    }
}