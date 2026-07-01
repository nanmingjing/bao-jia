-- 政策配置表
CREATE TABLE IF NOT EXISTS policy_config (
    id SERIAL PRIMARY KEY,
    config_type VARCHAR(50) NOT NULL,      -- 配置类型: BRAND_FACTOR, REGION_FACTOR, INSURER_DISCOUNT, BASE_PRICE
    config_key VARCHAR(100) NOT NULL,      -- 配置键: 如"宝马"、"北京"、"中国平安"
    config_value DECIMAL(10,4) NOT NULL,   -- 配置值: 系数或折扣
    description VARCHAR(255),               -- 描述
    enabled BOOLEAN DEFAULT TRUE,          -- 是否启用
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(config_type, config_key)
);

-- 初始化品牌系数
INSERT INTO policy_config (config_type, config_key, config_value, description) VALUES
('BRAND_FACTOR', '宝马', 1.30, '宝马品牌系数'),
('BRAND_FACTOR', '奔驰', 1.35, '奔驰品牌系数'),
('BRAND_FACTOR', '奥迪', 1.25, '奥迪品牌系数'),
('BRAND_FACTOR', '大众', 1.00, '大众品牌系数'),
('BRAND_FACTOR', '丰田', 0.95, '丰田品牌系数'),
('BRAND_FACTOR', '本田', 0.95, '本田品牌系数'),
('BRAND_FACTOR', '比亚迪', 0.90, '比亚迪品牌系数'),
('BRAND_FACTOR', '五菱', 0.80, '五菱品牌系数')
ON CONFLICT (config_type, config_key) DO NOTHING;

-- 初始化地区系数
INSERT INTO policy_config (config_type, config_key, config_value, description) VALUES
('REGION_FACTOR', '北京', 1.20, '北京地区系数'),
('REGION_FACTOR', '上海', 1.20, '上海地区系数'),
('REGION_FACTOR', '深圳', 1.15, '深圳地区系数'),
('REGION_FACTOR', '广州', 1.10, '广州地区系数'),
('REGION_FACTOR', '杭州', 1.05, '杭州地区系数'),
('REGION_FACTOR', '成都', 1.00, '成都地区系数'),
('REGION_FACTOR', '重庆', 1.00, '重庆地区系数')
ON CONFLICT (config_type, config_key) DO NOTHING;

-- 初始化保险公司折扣
INSERT INTO policy_config (config_type, config_key, config_value, description) VALUES
('INSURER_DISCOUNT', '中国平安', 0.85, '中国平安折扣'),
('INSURER_DISCOUNT', '中国人保', 0.90, '中国人保折扣'),
('INSURER_DISCOUNT', '太平洋保险', 0.88, '太平洋保险折扣'),
('INSURER_DISCOUNT', '阳光保险', 0.80, '阳光保险折扣')
ON CONFLICT (config_type, config_key) DO NOTHING;

-- 初始化基础保费
INSERT INTO policy_config (config_type, config_key, config_value, description) VALUES
('BASE_PRICE', 'default', 1500.00, '基础保费')
ON CONFLICT (config_type, config_key) DO NOTHING;