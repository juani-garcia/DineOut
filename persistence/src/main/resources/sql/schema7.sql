CREATE TABLE IF NOT EXISTS favorite (
    restaurant_id INTEGER,
    user_id INTEGER,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES account(id) ON DELETE CASCADE,
    PRIMARY KEY (restaurant_id, user_id)
);
