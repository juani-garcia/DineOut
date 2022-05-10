CREATE TABLE IF NOT EXISTS password_reset_token (
    id SERIAL,
    token TEXT,
    user_id INTEGER NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    is_used BOOLEAN DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES account(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

ALTER TABLE reservation
ADD COLUMN is_confirmed BOOLEAN NOT NULL
DEFAULT false
