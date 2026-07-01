package com.example.aaacar2.config;

import java.util.Map;

public class InsurerConfig {

    public static final Map<String, Double> DISCOUNT =
            Map.of(
                    "中国平安", 0.85,
                    "中国人保", 0.90,
                    "太平洋保险", 0.88,
                    "阳光保险", 0.80
            );

}