package com.example.aaacar2.repository;

import com.example.aaacar2.entity.RegionRule;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RegionRuleRepository {

    private final JdbcTemplate jdbcTemplate;

    public RegionRuleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询某地区当前生效的规则
     */
    public List<RegionRule> findActiveRulesByRegion(String region, LocalDate currentDate) {
        String sql = """
            SELECT id, region, rule_type, field_name, rule_content,
                   adjust_value, coefficient, effective_date, created_at, updated_at, enabled
            FROM region_rule
            WHERE region = ?
            AND enabled = true
            AND effective_date <= ?
            ORDER BY effective_date DESC
            """;
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(RegionRule.class),
                region,
                currentDate
        );
    }

    /**
     * 查询所有规则
     */
    public List<RegionRule> findAll() {
        String sql = """
            SELECT id, region, rule_type, field_name, rule_content,
                   adjust_value, coefficient, effective_date, created_at, updated_at, enabled
            FROM region_rule
            ORDER BY region, effective_date DESC
            """;
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(RegionRule.class)
        );
    }

    /**
     * 新增或更新规则
     */
    public int save(RegionRule rule) {
        String sql = """
            INSERT INTO region_rule (region, rule_type, field_name, rule_content, adjust_value, coefficient, effective_date, enabled)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT (region, rule_type, field_name)
            DO UPDATE SET rule_content = ?, adjust_value = ?, coefficient = ?, effective_date = ?, enabled = ?, updated_at = CURRENT_TIMESTAMP
            """;
        return jdbcTemplate.update(
                sql,
                rule.getRegion(),
                rule.getRuleType(),
                rule.getFieldName(),
                rule.getRuleContent(),
                rule.getAdjustValue(),
                rule.getCoefficient(),
                rule.getEffectiveDate(),
                rule.getEnabled() != null ? rule.getEnabled() : true,
                rule.getRuleContent(),
                rule.getAdjustValue(),
                rule.getCoefficient(),
                rule.getEffectiveDate(),
                rule.getEnabled() != null ? rule.getEnabled() : true
        );
    }

    /**
     * 删除规则
     */
    public int delete(Long id) {
        String sql = "DELETE FROM region_rule WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * 更新规则状态
     */
    public int updateEnabled(Long id, Boolean enabled) {
        String sql = "UPDATE region_rule SET enabled = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, enabled, id);
    }
}