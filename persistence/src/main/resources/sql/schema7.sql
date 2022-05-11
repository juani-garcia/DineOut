CREATE TABLE IF NOT EXISTS favorite (
    restaurant_id INTEGER,
    user_id INTEGER,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
    FOREIGN KEY (user_id) REFERENCES account(id),
    PRIMARY KEY (restaurant_id, user_id)
);
