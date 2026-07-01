package com.example.aaacar2.config;

import java.util.Map;

public class RegionConfig {

    public static final Map<String, Double> REGION_FACTOR =
            Map.of(
                    "北京", 1.20,
                    "上海", 1.20,
                    "深圳", 1.15,
                    "广州", 1.10,
                    "杭州", 1.05,
                    "成都", 1.00,
                    "重庆", 1.00
            );

}