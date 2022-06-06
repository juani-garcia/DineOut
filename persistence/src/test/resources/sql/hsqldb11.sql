CREATE TABLE IF NOT EXISTS restaurant_review (
                                                 id SERIAL PRIMARY KEY,
                                                 review TEXT,
                                                 rating INTEGER NOT NULL,
                                                 restaurant_id INTEGER,
                                                 user_id INTEGER,
                                                 FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES account(id) ON DELETE CASCADE
    );