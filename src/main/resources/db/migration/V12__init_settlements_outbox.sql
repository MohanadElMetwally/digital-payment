-- -----------------------------------------------------
-- Table: settlements_outbox
-- -----------------------------------------------------

CREATE TABLE settlements_outbox (
    id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    settlement_id   UUID            NOT NULL UNIQUE REFERENCES settlements(id),
    status          VARCHAR(50)     NOT NULL DEFAULT 'PENDING',
    attempt_count   INT             NOT NULL DEFAULT 0,
    last_error      VARCHAR(255),
    created_at      TIMESTAMP       NOT NULL DEFAULT now(),
    published_at    TIMESTAMP
);

CREATE INDEX idx_outbox_pending ON settlements_outbox (status, created_at) WHERE status = 'PENDING';
