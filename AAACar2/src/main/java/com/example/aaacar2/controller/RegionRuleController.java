package com.example.aaacar2.controller;

import com.example.aaacar2.entity.RegionRule;
import com.example.aaacar2.service.RegionRuleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/region-rule")
public class RegionRuleController {

    private final RegionRuleService regionRuleService;

    public RegionRuleController(RegionRuleService regionRuleService) {
        this.regionRuleService = regionRuleService;
    }

    /**
     * 获取所有地区规则
     */
    @GetMapping
    public List<RegionRule> getAllRules() {
        return regionRuleService.getAllRules();
    }

    /**
     * 获取某地区的规则
     */
    @GetMapping("/{region}")
    public Map<String, Object> getRulesByRegion(@PathVariable String region) {
        Map<String, Object> result = new HashMap<>();
        result.put("region", region);
        result.put("coefficient", regionRuleService.getRegionCoefficient(region));
        result.put("requiredFields", regionRuleService.getRequiredFields(region));
        result.put("rules", regionRuleService.getRulesByRegion(region));
        return result;
    }

    /**
     * 添加或更新规则
     */
    @PostMapping
    public String saveRule(@RequestBody RegionRule rule) {
        if (rule.getEffectiveDate() == null) {
            rule.setEffectiveDate(LocalDate.now());
        }
        if (rule.getEnabled() == null) {
            rule.setEnabled(true);
        }
        regionRuleService.saveRule(rule);
        return "规则已保存";
    }

    /**
     * 删除规则
     */
    @DeleteMapping("/{id}")
    public String deleteRule(@PathVariable Long id) {
        regionRuleService.deleteRule(id);
        return "规则已删除";
    }

    /**
     * 启用/禁用规则
     */
    @PutMapping("/{id}/toggle")
    public String toggleRule(@PathVariable Long id, @RequestParam boolean enabled) {
        regionRuleService.toggleRule(id, enabled);
        return enabled ? "规则已启用" : "规则已禁用";
    }

    /**
     * 刷新规则缓存
     */
    @PostMapping("/refresh")
    public String refreshCache() {
        regionRuleService.refreshCache();
        return "缓存已刷新";
    }
}