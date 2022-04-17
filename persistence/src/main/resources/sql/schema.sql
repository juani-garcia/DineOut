CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    UNIQUE(username)
);

CREATE TABLE IF NOT EXISTS restaurant (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    address TEXT NOT NULL,
    mail TEXT UNIQUE NOT NULL,
    detail TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation (
    reservation_id SERIAL,
    restaurant_id INTEGER,
    user_mail TEXT NOT NULL,
    amount INTEGER NOT NULL,
    date_time TIMESTAMP NOT NULL,
    comments TEXT NOT NULL,
    PRIMARY KEY (reservation_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);
