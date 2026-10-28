
CREATE TABLE leads (
                       id BIGSERIAL PRIMARY KEY,
                       full_name VARCHAR(100) NOT NULL,
                       phone VARCHAR(20) NOT NULL,
                       province VARCHAR(100) NOT NULL,
                       package_id VARCHAR(50) NOT NULL,
                       address VARCHAR(200),
                       source VARCHAR(50) NOT NULL DEFAULT 'website',
                       status VARCHAR(30) NOT NULL DEFAULT 'NEW',
                       consent BOOLEAN NOT NULL,
                       created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_leads_phone ON leads(phone);
CREATE INDEX idx_leads_status ON leads(status);
