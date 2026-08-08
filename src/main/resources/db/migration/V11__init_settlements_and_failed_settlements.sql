-- -----------------------------------------------------
-- Table: settlements
-- -----------------------------------------------------

CREATE TABLE settlements (
    id                       UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    bill_id                  UUID           NOT NULL,
    user_id                  UUID           NOT NULL,
    customer_number          VARCHAR(255)   NOT NULL,
    amount                   DECIMAL(12, 2) NOT NULL,
    currency                 VARCHAR(3)     NOT NULL,
    status                   VARCHAR(50)    NOT NULL DEFAULT 'PENDING',
    provider_idempotency_key UUID           NOT NULL,
    provider_reference       VARCHAR(255),
    attempt_count            INT            NOT NULL DEFAULT 0,
    last_attempted_at        TIMESTAMP,
    last_error               VARCHAR(255),
    processed_at             TIMESTAMP,
    created_at               TIMESTAMP      NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_settlements_bill_id UNIQUE (bill_id),
    CONSTRAINT uq_settlements_provider_idempotency_key UNIQUE (provider_idempotency_key),

    CONSTRAINT fk_settlements_bill
        FOREIGN KEY (bill_id) REFERENCES bills (id),
    CONSTRAINT fk_settlements_user
        FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_settlements_status ON settlements (status);

-- -----------------------------------------------------
-- Table: failed_settlements
-- -----------------------------------------------------

CREATE TABLE failed_settlements (
    id                 UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    settlement_id      UUID           NOT NULL,
    bill_id            UUID           NOT NULL,
    user_id            UUID           NOT NULL,
    customer_number    VARCHAR(255)   NOT NULL,
    amount             DECIMAL(12, 2) NOT NULL,
    currency           VARCHAR(3)     NOT NULL,
    failure_reason     VARCHAR(255)   NOT NULL,
    attempt_count      INT            NOT NULL,
    resolution_status  VARCHAR(50)    NOT NULL DEFAULT 'OPEN',
    resolution_notes   VARCHAR(255),
    resolved_by        VARCHAR(255),
    resolved_at        TIMESTAMP,
    created_at         TIMESTAMP      NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_failed_settlements_settlement_id UNIQUE (settlement_id),

    CONSTRAINT fk_failed_settlements_settlement
        FOREIGN KEY (settlement_id) REFERENCES settlements (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_failed_settlements_bill
        FOREIGN KEY (bill_id) REFERENCES bills (id),
    CONSTRAINT fk_failed_settlements_user
        FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_failed_settlements_resolution_status
    ON failed_settlements (resolution_status);