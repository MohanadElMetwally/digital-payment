-- -----------------------------------------------------
-- Table: billers
-- -----------------------------------------------------

CREATE TABLE billers (
    id               UUID PRIMARY KEY NOT NULL,
    name             VARCHAR(255)     NOT NULL,
    category         VARCHAR(255)     NOT NULL,
    service_provider VARCHAR(255)     NOT NULL,      
    created_at       TIMESTAMP        NOT NULL DEFAULT NOW()
);

-- -----------------------------------------------------
-- Table: bills
-- -----------------------------------------------------

CREATE TABLE bills (
    id                          UUID PRIMARY KEY NOT NULL,
    biller_id                   UUID             NOT NULL,
    user_id                     UUID             NOT NULL,
    external_bill_id            VARCHAR(255)     NOT NULL,    
    external_customer_number    VARCHAR(255)     NOT NULL,    
    external_customer_name      VARCHAR(255)     NOT NULL,    
    amount                      DECIMAL(12, 2)   NOT NULL,
    currency                    VARCHAR(3)       NOT NULL,    
    status                      VARCHAR(255)     NOT NULL DEFAULT 'UNPAID',
    billing_period_start        DATE             NOT NULL,
    billing_period_end          DATE             NOT NULL,
    due_date                    DATE             NOT NULL,
    last_synced_at              TIMESTAMP        NOT NULL DEFAULT NOW(),
    created_at                  TIMESTAMP        NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_bills_biller
        FOREIGN KEY (biller_id) REFERENCES billers (id),
    CONSTRAINT fk_bills_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE UNIQUE INDEX uq_bills_external_bill_id_biller_id
    ON bills (external_bill_id, biller_id);

CREATE INDEX idx_bills_user_id_biller_id_status
    ON bills (user_id, biller_id, status);