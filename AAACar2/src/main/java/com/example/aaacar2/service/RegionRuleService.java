package com.example.aaacar2.service;

import com.example.aaacar2.entity.RegionRule;
import com.example.aaacar2.repository.RegionRuleRepository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegionRuleService {

    private final RegionRuleRepository repository;

    // 缓存：地区 -> 规则列表
    private volatile Map<String, List<RegionRule>> rulesCache = new ConcurrentHashMap<>();
    private volatile LocalDate lastRefreshDate = LocalDate.MIN;

    public RegionRuleService(RegionRuleRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        try {
            refreshCache();
        } catch (Exception e) {
            // 初始化默认规则
            loadDefaultRules();
        }
    }

    /**
     * 刷新缓存
     */
    public synchronized void refreshCache() {
        List<RegionRule> allRules = repository.findAll();
        Map<String, List<RegionRule>> newCache = new ConcurrentHashMap<>();

        for (RegionRule rule : allRules) {
            if (Boolean.TRUE.equals(rule.getEnabled()) && isEffective(rule)) {
                newCache.computeIfAbsent(rule.getRegion(), k -> new java.util.ArrayList<>()).add(rule);
            }
        }

        rulesCache = newCache;
        lastRefreshDate = LocalDate.now();
    }

    /**
     * 检查规则是否生效
     */
    private boolean isEffective(RegionRule rule) {
        if (rule.getEffectiveDate() == null) {
            return true;
        }
        return !rule.getEffectiveDate().isAfter(LocalDate.now());
    }

    /**
     * 加载默认规则
     */
    private void loadDefaultRules() {
        Map<String, List<RegionRule>> defaultRules = new ConcurrentHashMap<>();

        // 北京规则示例
        defaultRules.put("北京", List.of(
                createRule("北京", "REQUIRED", "bodyColor", "车身颜色必填", 1.0),
                createRule("北京", "COEFFICIENT", "bodyColor", "北京车身颜色规则", 1.05)
        ));

        // 上海规则示例
        defaultRules.put("上海", List.of(
                createRule("上海", "REQUIRED", "engineNo", "发动机号必填", 1.0),
                createRule("上海", "COEFFICIENT", "engineNo", "上海发动机号规则", 1.03)
        ));

        // 深圳规则示例
        defaultRules.put("深圳", List.of(
                createRule("深圳", "COEFFICIENT", "electric", "电动车附加费", 1.10)
        ));

        rulesCache = defaultRules;
    }

    private RegionRule createRule(String region, String type, String field, String content, double coefficient) {
        RegionRule rule = new RegionRule();
        rule.setRegion(region);
        rule.setRuleType(type);
        rule.setFieldName(field);
        rule.setRuleContent(content);
        rule.setCoefficient(coefficient);
        rule.setEnabled(true);
        return rule;
    }

    /**
     * 获取某地区的所有生效规则
     */
    public List<RegionRule> getRulesByRegion(String region) {
        // 每天自动刷新一次缓存
        if (LocalDate.now().isAfter(lastRefreshDate)) {
            try {
                refreshCache();
            } catch (Exception e) {
                // 使用缓存
            }
        }
        return rulesCache.getOrDefault(region, List.of());
    }

    /**
     * 获取必填字段规则
     */
    public List<String> getRequiredFields(String region) {
        return getRulesByRegion(region).stream()
                .filter(r -> RegionRule.RuleType.REQUIRED.equals(r.getRuleType()))
                .map(RegionRule::getFieldName)
                .toList();
    }

    /**
     * 获取系数调整规则
     */
    public double getCoefficient(String region, String fieldName) {
        return getRulesByRegion(region).stream()
                .filter(r -> RegionRule.RuleType.COEFFICIENT.equals(r.getRuleType()))
                .filter(r -> fieldName == null || fieldName.equals(r.getFieldName()))
                .mapToDouble(RegionRule::getCoefficient)
                .reduce(1.0, (a, b) -> a * b);
    }

    /**
     * 获取地区综合系数
     */
    public double getRegionCoefficient(String region) {
        double coeff = 1.0;
        for (RegionRule rule : getRulesByRegion(region)) {
            if (RegionRule.RuleType.COEFFICIENT.equals(rule.getRuleType())) {
                coeff *= rule.getCoefficient();
            }
        }
        return coeff;
    }

    /**
     * 验证必填字段
     */
    public List<String> validateRequiredFields(String region, Map<String, Object> requestData) {
        List<String> missingFields = new java.util.ArrayList<>();
        for (String field : getRequiredFields(region)) {
            if (!requestData.containsKey(field) || requestData.get(field) == null ||
                    (requestData.get(field) instanceof String && ((String) requestData.get(field)).isEmpty())) {
                missingFields.add(field);
            }
        }
        return missingFields;
    }

    /**
     * 获取所有规则
     */
    public List<RegionRule> getAllRules() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            return rulesCache.values().stream().flatMap(List::stream).toList();
        }
    }

    /**
     * 保存规则
     */
    public void saveRule(RegionRule rule) {
        repository.save(rule);
        refreshCache();
    }

    /**
     * 删除规则
     */
    public void deleteRule(Long id) {
        repository.delete(id);
        refreshCache();
    }

    /**
     * 启用/禁用规则
     */
    public void toggleRule(Long id, boolean enabled) {
        repository.updateEnabled(id, enabled);
        refreshCache();
    }
}