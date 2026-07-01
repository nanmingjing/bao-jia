package com.example.aaacar2.repository;

import com.example.aaacar2.entity.InsurerPolicy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InsurerPolicyRepository {

    private final JdbcTemplate jdbcTemplate;

    public InsurerPolicyRepository(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // 一级：精确匹配
    public List<InsurerPolicy> exactMatch(
            String brand,
            String model,
            String region
    ) {

        String sql = """
        SELECT
            i.name AS insurer,
            p.brand,
            p.model,
            p.level,
            p.region,
            p.plan_tier AS planTier,
            p.premium_min AS premiumMin,
            p.premium_max AS premiumMax,
            p.commission_rate AS commissionRate,
            p.pass_rate AS passRate
        FROM insurer_policy p
        JOIN insurer i
        ON p.insurer_id = i.insurer_id
        WHERE p.brand = ?
        AND p.model = ?
        AND p.region = ?
        """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(InsurerPolicy.class),
                brand,
                model,
                region
        );
    }


    // 二级：关键词匹配
    public List<InsurerPolicy> keywordMatch(
            String brand,
            String region
    ) {

        String sql = """
        SELECT
            i.name AS insurer,
            p.brand,
            p.model,
            p.level,
            p.region,
            p.plan_tier AS planTier,
            p.premium_min AS premiumMin,
            p.premium_max AS premiumMax,
            p.commission_rate AS commissionRate,
            p.pass_rate AS passRate
        FROM insurer_policy p
        JOIN insurer i
        ON p.insurer_id = i.insurer_id
        WHERE p.brand = ?
        AND p.region = ?
        """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(InsurerPolicy.class),
                brand,
                region
        );
    }


    // 三级：正则匹配
    public List<InsurerPolicy> regexMatch(
            String model
    ) {

        String sql = """
        SELECT
            i.name AS insurer,
            p.brand,
            p.model,
            p.level,
            p.region,
            p.plan_tier AS planTier,
            p.premium_min AS premiumMin,
            p.premium_max AS premiumMax,
            p.commission_rate AS commissionRate,
            p.pass_rate AS passRate
        FROM insurer_policy p
        JOIN insurer i
        ON p.insurer_id = i.insurer_id
        WHERE p.model ~ ?
        """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(InsurerPolicy.class),
                model
        );
    }


    // 四级：模糊匹配
    public List<InsurerPolicy> fuzzyMatch(
            String model
    ) {

        String sql = """
        SELECT
            i.name AS insurer,
            p.brand,
            p.model,
            p.level,
            p.region,
            p.plan_tier AS planTier,
            p.premium_min AS premiumMin,
            p.premium_max AS premiumMax,
            p.commission_rate AS commissionRate,
            p.pass_rate AS passRate
        FROM insurer_policy p
        JOIN insurer i
        ON p.insurer_id = i.insurer_id
        WHERE p.model LIKE ?
        """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(InsurerPolicy.class),
                "%" + model + "%"
        );
    }


}
