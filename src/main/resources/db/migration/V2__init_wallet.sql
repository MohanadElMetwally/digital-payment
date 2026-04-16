-- -----------------------------------------------------
-- Table: wallets
-- -----------------------------------------------------

CREATE TABLE wallets (
    id         UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID           NOT NULL UNIQUE,
    currency   VARCHAR(10)    NOT NULL,
    balance    DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    status     VARCHAR(50)    NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP      NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_wallets_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);