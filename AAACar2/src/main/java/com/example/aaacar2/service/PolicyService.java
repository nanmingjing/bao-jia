package com.example.aaacar2.service;

import com.example.aaacar2.entity.PolicyConfig;
import com.example.aaacar2.repository.PolicyConfigRepository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PolicyService {

    private final PolicyConfigRepository repository;

    private volatile Map<String, Double> brandFactors = new ConcurrentHashMap<>();
    private volatile Map<String, Double> regionFactors = new ConcurrentHashMap<>();
    private volatile Map<String, Double> insurerDiscounts = new ConcurrentHashMap<>();
    private volatile double basePrice = 1500.0;

    public PolicyService(PolicyConfigRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        try {
            refreshCache();
        } catch (Exception e) {
            loadDefaultValues();
        }
    }

    private void loadDefaultValues() {
        brandFactors = new ConcurrentHashMap<>();
        brandFactors.put("宝马", 1.30);
        brandFactors.put("奔驰", 1.35);
        brandFactors.put("奥迪", 1.25);
        brandFactors.put("大众", 1.00);
        brandFactors.put("丰田", 0.95);
        brandFactors.put("本田", 0.95);
        brandFactors.put("比亚迪", 0.90);
        brandFactors.put("五菱", 0.80);

        regionFactors = new ConcurrentHashMap<>();
        regionFactors.put("北京", 1.20);
        regionFactors.put("上海", 1.20);
        regionFactors.put("深圳", 1.15);
        regionFactors.put("广州", 1.10);
        regionFactors.put("杭州", 1.05);
        regionFactors.put("成都", 1.00);
        regionFactors.put("重庆", 1.00);

        insurerDiscounts = new ConcurrentHashMap<>();
        insurerDiscounts.put("中国平安", 0.85);
        insurerDiscounts.put("中国人保", 0.90);
        insurerDiscounts.put("太平洋保险", 0.88);
        insurerDiscounts.put("阳光保险", 0.80);

        basePrice = 1500.0;
    }

    /**
     * 刷新缓存，从数据库重新加载配置
     */
    public synchronized void refreshCache() {
        List<PolicyConfig> brandConfigs = repository.findByType("BRAND_FACTOR");
        brandFactors = brandConfigs.isEmpty() 
                ? new ConcurrentHashMap<>() 
                : brandConfigs.stream()
                        .collect(Collectors.toMap(
                                PolicyConfig::getConfigKey,
                                c -> c.getConfigValue().doubleValue()
                        ));

        List<PolicyConfig> regionConfigs = repository.findByType("REGION_FACTOR");
        regionFactors = regionConfigs.isEmpty() 
                ? new ConcurrentHashMap<>() 
                : regionConfigs.stream()
                        .collect(Collectors.toMap(
                                PolicyConfig::getConfigKey,
                                c -> c.getConfigValue().doubleValue()
                        ));

        List<PolicyConfig> insurerConfigs = repository.findByType("INSURER_DISCOUNT");
        insurerDiscounts = insurerConfigs.isEmpty() 
                ? new ConcurrentHashMap<>() 
                : insurerConfigs.stream()
                        .collect(Collectors.toMap(
                                PolicyConfig::getConfigKey,
                                c -> c.getConfigValue().doubleValue()
                        ));

        List<PolicyConfig> baseConfigs = repository.findByType("BASE_PRICE");
        if (!baseConfigs.isEmpty()) {
            basePrice = baseConfigs.get(0).getConfigValue().doubleValue();
        }
    }

    public double getBrandFactor(String brand) {
        return brandFactors.getOrDefault(brand, 1.0);
    }

    public double getRegionFactor(String region) {
        return regionFactors.getOrDefault(region, 1.0);
    }

    public double getInsurerDiscount(String insurer) {
        return insurerDiscounts.getOrDefault(insurer, 1.0);
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Map<String, Double> getAllBrandFactors() {
        return new ConcurrentHashMap<>(brandFactors);
    }

    public Map<String, Double> getAllRegionFactors() {
        return new ConcurrentHashMap<>(regionFactors);
    }

    public Map<String, Double> getAllInsurerDiscounts() {
        return new ConcurrentHashMap<>(insurerDiscounts);
    }

    /**
     * 更新或新增配置
     */
    public void saveConfig(PolicyConfig config) {
        repository.save(config);
        refreshCache();
    }

    /**
     * 快速更新配置值
     */
    public void updateConfigValue(String configType, String configKey, Double value) {
        repository.updateValue(configType, configKey, value);
        refreshCache();
    }

    /**
     * 删除配置
     */
    public void deleteConfig(Long id) {
        repository.delete(id);
        refreshCache();
    }

    /**
     * 获取所有配置
     */
    public List<PolicyConfig> getAllConfigs() {
        return repository.findAll();
    }
}