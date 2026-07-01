CREATE TABLE IF NOT EXISTS policy_config (
    id SERIAL PRIMARY KEY,
    config_type VARCHAR(50) NOT NULL,
    config_key VARCHAR(100) NOT NULL,
    config_value DECIMAL(10,4) NOT NULL,
    description VARCHAR(255),
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(config_type, config_key)
);

INSERT INTO policy_config (config_type, config_key, config_value, description) VALUES
('BRAND_FACTOR', '宝马', 1.30, '宝马品牌系数'),
('BRAND_FACTOR', '奔驰', 1.35, '奔驰品牌系数'),
('BRAND_FACTOR', '奥迪', 1.25, '奥迪品牌系数'),
('BRAND_FACTOR', '大众', 1.00, '大众品牌系数'),
('BRAND_FACTOR', '丰田', 0.95, '丰田品牌系数'),
('BRAND_FACTOR', '本田', 0.95, '本田品牌系数'),
('BRAND_FACTOR', '比亚迪', 0.90, '比亚迪品牌系数'),
('BRAND_FACTOR', '五菱', 0.80, '五菱品牌系数'),
('REGION_FACTOR', '北京', 1.20, '北京地区系数'),
('REGION_FACTOR', '上海', 1.20, '上海地区系数'),
('REGION_FACTOR', '深圳', 1.15, '深圳地区系数'),
('REGION_FACTOR', '广州', 1.10, '广州地区系数'),
('REGION_FACTOR', '杭州', 1.05, '杭州地区系数'),
('REGION_FACTOR', '成都', 1.00, '成都地区系数'),
('REGION_FACTOR', '重庆', 1.00, '重庆地区系数'),
('INSURER_DISCOUNT', '中国平安', 0.85, '中国平安折扣'),
('INSURER_DISCOUNT', '中国人保', 0.90, '中国人保折扣'),
('INSURER_DISCOUNT', '太平洋保险', 0.88, '太平洋保险折扣'),
('INSURER_DISCOUNT', '阳光保险', 0.80, '阳光保险折扣'),
('BASE_PRICE', 'default', 1500.00, '基础保费')
ON CONFLICT (config_type, config_key) DO NOTHING;

-- 地区规则表
CREATE TABLE IF NOT EXISTS region_rule (
    id SERIAL PRIMARY KEY,
    region VARCHAR(50) NOT NULL,
    rule_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(100),
    rule_content VARCHAR(255),
    adjust_value DECIMAL(10,4),
    coefficient DECIMAL(10,4) DEFAULT 1.0,
    effective_date DATE,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(region, rule_type, field_name)
);

-- 初始化示例规则
INSERT INTO region_rule (region, rule_type, field_name, rule_content, coefficient, effective_date, enabled) VALUES
('北京', 'REQUIRED', 'bodyColor', '北京：车身颜色必填', 1.0, '2025-06-01', true),
('北京', 'COEFFICIENT', 'bodyColor', '北京：车身颜色附加系数', 1.05, '2025-06-01', true),
('上海', 'REQUIRED', 'engineNo', '上海：发动机号必填', 1.0, '2025-06-01', true),
('上海', 'COEFFICIENT', 'engineNo', '上海：发动机号附加系数', 1.03, '2025-06-01', true),
('深圳', 'COEFFICIENT', 'electric', '深圳：电动车附加费', 1.10, '2025-06-01', true)
ON CONFLICT (region, rule_type, field_name) DO NOTHING;