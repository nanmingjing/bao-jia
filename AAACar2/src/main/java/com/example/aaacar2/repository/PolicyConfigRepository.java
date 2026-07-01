package com.example.aaacar2.repository;

import com.example.aaacar2.entity.PolicyConfig;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PolicyConfigRepository {

    private final JdbcTemplate jdbcTemplate;

    public PolicyConfigRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PolicyConfig> findByType(String configType) {
        String sql = """
            SELECT id, config_type, config_key, config_value, description, enabled, created_at, updated_at
            FROM policy_config
            WHERE config_type = ? AND enabled = true
            """;
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(PolicyConfig.class),
                configType
        );
    }

    public List<PolicyConfig> findAll() {
        String sql = """
            SELECT id, config_type, config_key, config_value, description, enabled, created_at, updated_at
            FROM policy_config
            ORDER BY config_type, config_key
            """;
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(PolicyConfig.class)
        );
    }

    public int save(PolicyConfig config) {
        String sql = """
            INSERT INTO policy_config (config_type, config_key, config_value, description, enabled)
            VALUES (?, ?, ?, ?, ?)
            ON CONFLICT (config_type, config_key)
            DO UPDATE SET config_value = ?, description = ?, enabled = ?, updated_at = CURRENT_TIMESTAMP
            """;
        return jdbcTemplate.update(
                sql,
                config.getConfigType(),
                config.getConfigKey(),
                config.getConfigValue(),
                config.getDescription(),
                config.getEnabled() != null ? config.getEnabled() : true,
                config.getConfigValue(),
                config.getDescription(),
                config.getEnabled() != null ? config.getEnabled() : true
        );
    }

    public int delete(Long id) {
        String sql = "DELETE FROM policy_config WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int updateValue(String configType, String configKey, Double configValue) {
        String sql = """
            UPDATE policy_config
            SET config_value = ?, updated_at = CURRENT_TIMESTAMP
            WHERE config_type = ? AND config_key = ?
            """;
        return jdbcTemplate.update(sql, configValue, configType, configKey);
    }
}