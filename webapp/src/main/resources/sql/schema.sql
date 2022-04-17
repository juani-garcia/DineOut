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
    ordering INTEGER NOT NULL,
    FOREIGN KEY (section_id) REFERENCES menu_section (id) ON DELETE CASCADE,
    UNIQUE (ordering, section_id)
);

CREATE TABLE IF NOT EXISTS category (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS restaurant_category (
    restaurant_id INTEGER,
    category_id INTEGER,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE,
    PRIMARY KEY (restaurant_id, category_id)
);


INSERT INTO category VALUES (default, 'Italian') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'American') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Spanish') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Pizza') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Steakhouse') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Bar') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Chinese') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Viet-Thai') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Burgers') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Japanese') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Sushi') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Veggie') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Indian') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Empanadas') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Boulangerie') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Coffee') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Brewery') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Peruvian') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Nikkei') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Arabian') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Mexican') ON CONFLICT DO NOTHING;
INSERT INTO category VALUES (default, 'Gelato') ON CONFLICT DO NOTHING;
