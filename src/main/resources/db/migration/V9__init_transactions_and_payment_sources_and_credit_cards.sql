-- ------------------------------------------------------------
-- Table: transactions
-- ------------------------------------------------------------

CREATE TABLE transactions (
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id            UUID                              NOT NULL,
    idempotency_key    UUID                              NOT NULL,
    reference_number   VARCHAR(255)                      NOT NULL,
    type               VARCHAR(255)                      NOT NULL,
    status             VARCHAR(255)                      NOT NULL,
    amount             DECIMAL(12, 2)                    NOT NULL,
    currency           VARCHAR(3)                        NOT NULL,
    external_reference VARCHAR(255),
    failure_reason     VARCHAR(255),
    created_at         TIMESTAMP                         NOT NULL DEFAULT NOW(),
    completed_at       TIMESTAMP,

    CONSTRAINT uq_transactions_idempotency_key UNIQUE (idempotency_key),
    CONSTRAINT uq_transactions_reference_number        UNIQUE (reference_number)
);
CREATE INDEX idx_transactions_user_id                ON transactions (user_id, idempotency_key);
CREATE INDEX idx_transactions_status                 ON transactions (status);
CREATE INDEX idx_transactions_external_reference     ON transactions (external_reference);
CREATE INDEX idx_transactions_created_at             ON transactions (created_at DESC);

-- ------------------------------------------------------------
-- Table: payment_sources
-- ------------------------------------------------------------

CREATE TABLE payment_sources (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transaction_id UUID                              NOT NULL,
    source_type    VARCHAR(255)                      NOT NULL,
    source_id      UUID                              NOT NULL,
    amount         DECIMAL(12, 2)                    NOT NULL,
    created_at     TIMESTAMP                         NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_payment_sources_transaction
        FOREIGN KEY (transaction_id)
        REFERENCES transactions (id)
        ON DELETE CASCADE
);
CREATE INDEX idx_payment_sources_transaction_id ON payment_sources (transaction_id);
CREATE INDEX idx_payment_sources_source_id      ON payment_sources (source_id);

-- ------------------------------------------------------------
-- Table: credit_cards
-- ------------------------------------------------------------

CREATE TABLE credit_cards (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             UUID                              NOT NULL,
    brand               VARCHAR(50)                       NOT NULL,
    last_four           VARCHAR(50)                       NOT NULL,
    expiry_month        VARCHAR(50)                       NOT NULL,
    expiry_year         VARCHAR(50)                       NOT NULL,
    payment_method_id   VARCHAR(255)                      NOT NULL,
    status              VARCHAR(10)                       NOT NULL DEFAULT 'ACTIVE',
    is_default          BOOLEAN                           NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMP                         NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_credit_cards_user_id ON credit_cards (user_id);
CREATE INDEX idx_credit_cards_status  ON credit_cards (status);

-- -----------------------------------------------------
-- Table: payment_customers
-- -----------------------------------------------------

CREATE TABLE payment_customers (
    user_id UUID PRIMARY KEY REFERENCES users(id),
    customer_id VARCHAR           NOT NULL UNIQUE,
    created_at TIMESTAMP          NOT NULL
);
