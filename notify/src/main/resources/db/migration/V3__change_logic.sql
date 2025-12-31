-- 다른 테이블 삭제 코드 추가

DROP TABLE IF EXISTS notification_channels;
DROP TABLE IF EXISTS notification_templates;
DROP TABLE IF EXISTS notifications;

CREATE TABLE fcm_tokens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    token VARCHAR(100) NOT NULL,
    device_type VARCHAR(20) NOT NULL,
    browser_type VARCHAR(20) NOT NULL,
    updated_at DATE NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;