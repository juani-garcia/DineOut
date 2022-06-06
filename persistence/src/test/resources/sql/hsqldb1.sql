CREATE TABLE IF NOT EXISTS account (
                                       id SERIAL PRIMARY KEY,
                                       username TEXT NOT NULL UNIQUE,
                                       password TEXT NOT NULL,
                                       first_name TEXT NOT NULL DEFAULT '',
                                       last_name TEXT NOT NULL DEFAULT '',
                                       UNIQUE(username)
);

CREATE TABLE IF NOT EXISTS restaurant (
                                          id SERIAL PRIMARY KEY,
                                          name TEXT NOT NULL,
                                          image_id INTEGER,

    address TEXT NOT NULL,
                                          mail TEXT UNIQUE NOT NULL,
                                          zone_id INTEGER DEFAULT null,
                                          detail TEXT NOT NULL,
                                          FOREIGN KEY (image_id) REFERENCES image (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS reservation (
                                           reservation_id SERIAL,
                                           restaurant_id INTEGER,
                                           user_mail TEXT NOT NULL,
                                           amount INTEGER NOT NULL,
                                           date_time TIMESTAMP NOT NULL,
                                           comments TEXT NOT NULL,
                                           is_confirmed BOOLEAN NOT NULL DEFAULT false,
                                           PRIMARY KEY (reservation_id),
                                           FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE TABLE IF NOT EXISTS menu_section (
                                            id SERIAL PRIMARY KEY,
                                            name TEXT NOT NULL,
                                            restaurant_id INTEGER NOT NULL,
                                            ordering INTEGER NOT NULL,
                                            FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
                                            UNIQUE (ordering, restaurant_id)
);

CREATE TABLE IF NOT EXISTS menu_item (
                                         id SERIAL PRIMARY KEY,
                                         name TEXT NOT NULL,
                                         detail TEXT,
                                         price DOUBLE PRECISION NOT NULL,
                                         section_id INTEGER NOT NULL,
                                         image_id INTEGER DEFAULT null,
                                         ordering INTEGER NOT NULL,
                                         FOREIGN KEY (image_id) REFERENCES image(id) ON DELETE CASCADE,
                                         FOREIGN KEY (section_id) REFERENCES menu_section (id) ON DELETE CASCADE,
                                         UNIQUE (ordering, section_id)
);

CREATE TABLE IF NOT EXISTS restaurant_category (
                                                   restaurant_id INTEGER,
                                                   category_id INTEGER,
                                                   FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
                                                   PRIMARY KEY (restaurant_id, category_id)
);

CREATE TABLE IF NOT EXISTS restaurant_opening_hours (
                                                        restaurant_id INTEGER,
                                                        opening_hours_id INTEGER,
                                                        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
                                                        PRIMARY KEY (restaurant_id, opening_hours_id)
);

CREATE TABLE IF NOT EXISTS image (
                                     id  SERIAL PRIMARY KEY,
                                     source VARBINARY(100)
);

CREATE TABLE IF NOT EXISTS favorite (
                                        restaurant_id INTEGER,
                                        user_id INTEGER,
                                        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
                                        FOREIGN KEY (user_id) REFERENCES restaurant(id),
                                        PRIMARY KEY (restaurant_id, user_id)
);
