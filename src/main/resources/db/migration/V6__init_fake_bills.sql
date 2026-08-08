-- -----------------------------------------------------
-- Table: fake_bills
-- -----------------------------------------------------

CREATE TABLE fake_bills (
    id                      UUID PRIMARY KEY    NOT NULL DEFAULT gen_random_uuid(),
    customer_number         VARCHAR(255)        NOT NULL,
    customer_name           VARCHAR(255)        NOT NULL,
    provider                VARCHAR(255)        NOT NULL,
    amount                  DECIMAL(12, 2)      NOT NULL,
    currency                VARCHAR(3)          NOT NULL,
    status                  VARCHAR(255)        NOT NULL DEFAULT 'UNPAID',
    billing_period_start    DATE                NOT NULL,
    billing_period_end      DATE                NOT NULL,
    due_date                DATE                NOT NULL,
    created_at              TIMESTAMP           NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_fake_bills_customer_number
    ON fake_bills (customer_number);
