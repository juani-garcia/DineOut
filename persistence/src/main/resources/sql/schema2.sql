-- Add user reference to restaurant.
ALTER TABLE restaurant ADD COLUMN user_id INTEGER DEFAULT null;
ALTER TABLE restaurant ADD FOREIGN KEY (user_id) REFERENCES account(id) ON DELETE CASCADE;

-- Add zone to restaurant.
ALTER TABLE restaurant ADD COLUMN zone_id INTEGER DEFAULT null;
