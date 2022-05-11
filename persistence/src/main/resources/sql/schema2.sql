-- Add user reference to restaurant.
ALTER TABLE restaurant ADD COLUMN IF NOT EXISTS user_id INTEGER DEFAULT null;
ALTER TABLE restaurant
    DROP CONSTRAINT IF EXISTS restaurant_user_id_fkey;
ALTER TABLE restaurant ADD CONSTRAINT restaurant_user_id_fkey FOREIGN KEY (user_id) REFERENCES account(id) ON DELETE CASCADE;

-- Add zone to restaurant.
ALTER TABLE restaurant ADD COLUMN IF NOT EXISTS zone_id INTEGER DEFAULT null;
