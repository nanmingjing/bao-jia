package com.example.aaacar2.controller;

import com.example.aaacar2.entity.PolicyConfig;
import com.example.aaacar2.service.PolicyService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/policy")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * 获取所有配置
     */
    @GetMapping
    public List<PolicyConfig> getAllConfigs() {
        return policyService.getAllConfigs();
    }

    /**
     * 获取当前生效的配置（分组展示）
     */
    @GetMapping("/current")
    public Map<String, Object> getCurrentConfig() {
        Map<String, Object> result = new HashMap<>();
        result.put("basePrice", policyService.getBasePrice());
        result.put("brandFactors", policyService.getAllBrandFactors());
        result.put("regionFactors", policyService.getAllRegionFactors());
        result.put("insurerDiscounts", policyService.getAllInsurerDiscounts());
        return result;
    }

    /**
     * 新增或更新配置
     */
    @PostMapping
    public String saveConfig(@RequestBody PolicyConfig config) {
        policyService.saveConfig(config);
        return "配置已保存";
    }

    /**
     * 快速更新配置值
     */
    @PutMapping("/{type}/{key}")
    public String updateValue(
            @PathVariable String type,
            @PathVariable String key,
            @RequestParam Double value
    ) {
        policyService.updateConfigValue(type, key, value);
        return "配置已更新";
    }

    /**
     * 删除配置
     */
    @DeleteMapping("/{id}")
    public String deleteConfig(@PathVariable Long id) {
        policyService.deleteConfig(id);
        return "配置已删除";
    }

    /**
     * 手动刷新缓存
     */
    @PostMapping("/refresh")
    public String refreshCache() {
        policyService.refreshCache();
        return "缓存已刷新";
    }
}