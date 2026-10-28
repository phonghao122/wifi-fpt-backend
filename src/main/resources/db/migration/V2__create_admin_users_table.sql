
-- Tao bang admin_users neu chua ton tai
CREATE TABLE IF NOT EXISTS admin_users (
                                           id BIGSERIAL PRIMARY KEY,
                                           username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL DEFAULT 'ADMIN',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

-- Tao index neu chua ton tai
CREATE UNIQUE INDEX IF NOT EXISTS idx_admin_users_username
    ON admin_users(username);
