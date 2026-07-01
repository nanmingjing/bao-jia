package com.example.aaacar2.service;

import com.example.aaacar2.dto.QuoteRequest;
import com.example.aaacar2.dto.QuoteResponse;
import com.example.aaacar2.entity.InsurerGroupResponse;
import com.example.aaacar2.entity.InsurerPolicy;
import com.example.aaacar2.entity.RegionRule;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final PolicySearchService policySearchService;
    private final RegionRuleService regionRuleService;

    public QuoteService(
            PolicySearchService policySearchService,
            RegionRuleService regionRuleService
    ) {
        this.policySearchService = policySearchService;
        this.regionRuleService = regionRuleService;
    }

    public QuoteResponse quote(
            QuoteRequest request
    ) {
        QuoteResponse response = new QuoteResponse();
        String region = request.getRegion();

        // 获取地区规则系数
        double regionCoefficient = regionRuleService.getRegionCoefficient(region);
        List<RegionRule> regionRules = regionRuleService.getRulesByRegion(region);

        // 收集规则描述
        StringBuilder ruleMsg = new StringBuilder();
        for (RegionRule rule : regionRules) {
            if (RegionRule.RuleType.COEFFICIENT.equals(rule.getRuleType())) {
                if (ruleMsg.length() > 0) {
                    ruleMsg.append("; ");
                }
                ruleMsg.append(rule.getRuleContent())
                        .append("(系数:")
                        .append(rule.getCoefficient())
                        .append(")");
            }
        }

        // 查询保险产品
        List<InsurerPolicy> policies =
                policySearchService.search(
                        request.getBrand(),
                        request.getModel(),
                        request.getRegion()
                );

        // 4. 按保险公司分组
        Map<String, List<InsurerPolicy>> insurerMap =
                policies.stream()
                        .collect(
                                Collectors.groupingBy(
                                        InsurerPolicy::getInsurer
                                )
                        );

        List<InsurerGroupResponse> result =
                new ArrayList<>();

        for (String insurer : insurerMap.keySet()) {

            List<InsurerPolicy> insurerPlans =
                    insurerMap.get(insurer);

            // 每个档位只保留一条
            Map<String, InsurerPolicy> tierMap =
                    insurerPlans.stream()
                            .collect(
                                    Collectors.toMap(
                                            InsurerPolicy::getPlanTier,
                                            p -> applyRegionCoefficient(p, regionCoefficient),

                                            // 冲突时选择通过率更高的
                                            (p1, p2) -> {
                                                if (p1.getPassRate() > p2.getPassRate()) {
                                                    return p1;
                                                }
                                                if (p1.getPassRate() < p2.getPassRate()) {
                                                    return p2;
                                                }
                                                return p1.getPremiumMin() <= p2.getPremiumMin() ? p1 : p2;
                                            }
                                    )
                            );

            List<InsurerPolicy> threePlans = new ArrayList<>();

            if (tierMap.containsKey("基础")) {
                threePlans.add(tierMap.get("基础"));
            }
            if (tierMap.containsKey("标准")) {
                threePlans.add(tierMap.get("标准"));
            }
            if (tierMap.containsKey("增值")) {
                threePlans.add(tierMap.get("增值"));
            }

            InsurerGroupResponse group = new InsurerGroupResponse();
            group.setInsurer(insurer);
            group.setPlans(threePlans);

            result.add(group);
        }

        // 5. 设置响应
        response.setPlans(result);
        response.setValid(true);
        response.setRegionCoefficient(regionCoefficient);
        if (ruleMsg.length() > 0) {
            response.setRegionRuleMessage(ruleMsg.toString());
        }

        return response;
    }

    /**
     * 应用地区系数到保费
     */
    private InsurerPolicy applyRegionCoefficient(InsurerPolicy policy, double regionCoefficient) {
        if (regionCoefficient == 1.0) {
            return policy;
        }

        InsurerPolicy adjusted = new InsurerPolicy();
        adjusted.setInsurer(policy.getInsurer());
        adjusted.setBrand(policy.getBrand());
        adjusted.setModel(policy.getModel());
        adjusted.setLevel(policy.getLevel());
        adjusted.setRegion(policy.getRegion());
        adjusted.setPlanTier(policy.getPlanTier());
        adjusted.setCommissionRate(policy.getCommissionRate());
        adjusted.setPassRate(policy.getPassRate());

        if (policy.getPremiumMin() != null) {
            adjusted.setPremiumMin(policy.getPremiumMin() * regionCoefficient);
        }
        if (policy.getPremiumMax() != null) {
            adjusted.setPremiumMax(policy.getPremiumMax() * regionCoefficient);
        }

        return adjusted;
    }
}