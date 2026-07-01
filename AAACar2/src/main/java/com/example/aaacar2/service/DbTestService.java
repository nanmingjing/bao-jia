package com.example.aaacar2.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DbTestService {

    private final JdbcTemplate jdbcTemplate;

    public DbTestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String test() {

        return jdbcTemplate.queryForObject(
                "select current_database()",
                String.class
        );
    }
}