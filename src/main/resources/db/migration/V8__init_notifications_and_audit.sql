-- ------------------------------------------------------------
-- Table: notifications
-- ------------------------------------------------------------

CREATE TABLE notifications (
    id          UUID                        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     UUID                        NOT NULL,
    title       VARCHAR(255)                NOT NULL,
    message     VARCHAR(1000)               NOT NULL,
    type        VARCHAR(100)                NOT NULL,
    is_read     BOOLEAN                     NOT NULL DEFAULT FALSE,
    read_at     TIMESTAMP,
    created_at  TIMESTAMP                   NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_read_at CHECK (
        (is_read = FALSE AND read_at IS NULL) OR
        (is_read = TRUE  AND read_at IS NOT NULL)
    )
);
CREATE INDEX idx_notifications_user_id         ON notifications (user_id);
CREATE INDEX idx_notifications_user_unread     ON notifications (user_id) WHERE is_read = FALSE;
CREATE INDEX idx_notifications_type            ON notifications (type);
CREATE INDEX idx_notifications_created_at      ON notifications (created_at DESC);

-- ------------------------------------------------------------
-- Table: audit_logs
-- ------------------------------------------------------------

CREATE TABLE audit_logs (
    id           UUID                        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID,
    action       VARCHAR(100)                NOT NULL,
    entity_type  VARCHAR(100)                NOT NULL,
    entity_id    UUID,
    old_value    VARCHAR(255),
    new_value    VARCHAR(255),
    created_at   TIMESTAMP                  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_audit_logs_user_id            ON audit_logs (user_id);
CREATE INDEX idx_audit_logs_action             ON audit_logs (action);
CREATE INDEX idx_audit_logs_entity             ON audit_logs (entity_type, entity_id);
CREATE INDEX idx_audit_logs_created_at         ON audit_logs (created_at DESC);