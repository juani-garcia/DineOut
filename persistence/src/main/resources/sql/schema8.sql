CREATE TABLE IF NOT EXISTS password_reset_token (
    id SERIAL,
    token TEXT,
    user_id INTEGER NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES account(id),
    PRIMARY KEY (id)
);
