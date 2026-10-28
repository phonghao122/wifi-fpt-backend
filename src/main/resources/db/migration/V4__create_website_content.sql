
-- Nội dung các khu vực trên website
CREATE TABLE website_content (
                                 id BIGSERIAL PRIMARY KEY,
                                 section_key VARCHAR(100) NOT NULL UNIQUE,
                                 content JSONB NOT NULL,
                                 updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Danh sách gói cước
CREATE TABLE internet_packages (
                                   id BIGSERIAL PRIMARY KEY,
                                   code VARCHAR(50) NOT NULL UNIQUE,
                                   name VARCHAR(100) NOT NULL,
                                   description TEXT,
                                   price INTEGER NOT NULL CHECK (price >= 0),
                                   speed VARCHAR(100),
                                   features JSONB NOT NULL DEFAULT '[]'::jsonb,
                                   featured BOOLEAN NOT NULL DEFAULT FALSE,
                                   active BOOLEAN NOT NULL DEFAULT TRUE,
                                   display_order INTEGER NOT NULL DEFAULT 0,
                                   updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);
