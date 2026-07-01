package com.example.aaacar2.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 地区规则配置
 * 用于配置各地区的特殊报价规则
 */
public class RegionRule {

    private Long id;
    private String region;                    // 地区
    private String ruleType;                  // 规则类型: REQUIRED/ADJUSTMENT/COEFFICIENT
    private String fieldName;                  // 字段名: bodyColor, engineNo, etc.
    private String ruleContent;               // 规则内容描述
    private Double adjustValue;               // 调整值（如加价百分比）
    private Double coefficient;                // 系数（如 1.1 表示加价10%）
    private LocalDate effectiveDate;          // 生效日期
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean enabled;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public Double getAdjustValue() {
        return adjustValue;
    }

    public void setAdjustValue(Double adjustValue) {
        this.adjustValue = adjustValue;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 规则类型常量
     */
    public static class RuleType {
        public static final String REQUIRED = "REQUIRED";       // 必填字段
        public static final String ADJUSTMENT = "ADJUSTMENT";   // 价格调整
        public static final String COEFFICIENT = "COEFFICIENT"; // 系数调整
    }
}