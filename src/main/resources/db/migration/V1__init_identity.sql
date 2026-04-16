-- -----------------------------------------------------
-- Table: users
-- -----------------------------------------------------

CREATE TABLE users (
    id            UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    username      VARCHAR(50)   NOT NULL,
    email         VARCHAR(255)  NOT NULL,
    phone         VARCHAR(20)   NOT NULL,
    password_hash VARCHAR(255)  NOT NULL,
    role          VARCHAR(50)   NOT NULL DEFAULT 'USER',
    status        VARCHAR(50)   NOT NULL DEFAULT 'ACTIVE',
    created_at    TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP,

    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email    UNIQUE (email),
    CONSTRAINT uq_users_phone    UNIQUE (phone)
);

-- -----------------------------------------------------
-- Table: user_profiles
-- -----------------------------------------------------

CREATE TABLE user_profiles (
    user_id       UUID PRIMARY KEY,
    first_name    VARCHAR(100),
    last_name     VARCHAR(100),
    date_of_birth DATE,
    country       VARCHAR(100),
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,

    CONSTRAINT fk_user_profiles_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);