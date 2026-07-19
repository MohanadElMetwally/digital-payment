-- -----------------------------------------------------
-- Table: bill_payments
-- -----------------------------------------------------

CREATE TABLE bill_payments (
    id             UUID PRIMARY KEY    NOT NULL,
    transaction_id UUID                NOT NULL,
    bill_id        UUID                NOT NULL,
    amount         DECIMAL(12, 2)      NOT NULL,
    status         VARCHAR(255)        NOT NULL DEFAULT 'PENDING',
    paid_at        TIMESTAMP,

    CONSTRAINT fk_bill_payments_bill_id 
        FOREIGN KEY (bill_id) REFERENCES bills(id),
    CONSTRAINT fk_bill_payments_transaction_id 
        FOREIGN KEY (transaction_id)
        REFERENCES transactions (id)
        ON DELETE CASCADE
);
CREATE INDEX idx_bill_payments_bill_id ON bill_payments (bill_id);
CREATE INDEX idx_bill_payments_transaction_id ON bill_payments (transaction_id);

-- -----------------------------------------------------
-- Table: wallet_transactions
-- -----------------------------------------------------

CREATE TABLE wallet_transactions (
    id              UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    wallet_id       UUID           NOT NULL,
    transaction_id  UUID           NOT NULL,
    type            VARCHAR(50)    NOT NULL,
    status          VARCHAR(50)    NOT NULL,
    balance_after   DECIMAL(12, 2) NOT NULL,
    amount          DECIMAL(12, 2) NOT NULL,
    created_at      TIMESTAMP      NOT NULL DEFAULT NOW(),
    completed_at    TIMESTAMP,

    CONSTRAINT uq_wallet_transactions_transaction_id UNIQUE (transaction_id),

    CONSTRAINT fk_wallet_transactions_wallet
        FOREIGN KEY (wallet_id)
        REFERENCES wallets (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_wallet_transactions_transaction
        FOREIGN KEY (transaction_id)
        REFERENCES transactions (id)
        ON DELETE CASCADE
);
CREATE INDEX idx_wallet_transactions_wallet_id ON wallet_transactions (wallet_id);
CREATE INDEX idx_wallet_transactions_transaction_id ON wallet_transactions (transaction_id);