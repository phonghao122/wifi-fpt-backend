
-- V3: Mo rong bang leads cho he thong CRM

-- Ghi chu tu van noi bo
ALTER TABLE leads
    ADD COLUMN admin_note TEXT;

-- Thoi diem cap nhat khach hang
ALTER TABLE leads
    ADD COLUMN updated_at TIMESTAMPTZ
        NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Thoi diem lien he dau tien
ALTER TABLE leads
    ADD COLUMN contacted_at TIMESTAMPTZ;

-- Gioi han trang thai hop le
ALTER TABLE leads
    ADD CONSTRAINT chk_leads_status
        CHECK (
            status IN (
                       'NEW',
                       'CONTACTED',
                       'CONVERTED',
                       'CANCELLED'
                )
            );

-- Ho tro sap xep khach hang moi nhat
CREATE INDEX idx_leads_created_at
    ON leads(created_at DESC);
