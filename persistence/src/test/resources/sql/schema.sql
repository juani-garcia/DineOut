CREATE TABLE IF NOT EXISTS restaurant (
                                          id INTEGER IDENTITY PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL,
                                          address VARCHAR(100) NOT NULL,
                                          mail VARCHAR(100) UNIQUE NOT NULL,
                                          detail VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation (
                                           reservation_id INTEGER IDENTITY PRIMARY KEY,
                                           restaurant_id INTEGER,
                                           user_mail VARCHAR(100) NOT NULL,
                                           amount INTEGER NOT NULL,
                                           date_time TIMESTAMP NOT NULL,
                                           comments TIMESTAMP NOT NULL,
                                           FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);
