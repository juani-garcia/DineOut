-- Add first and last name to account.
ALTER TABLE account ADD COLUMN IF NOT EXISTS first_name TEXT NOT NULL DEFAULT '';
ALTER TABLE account ADD COLUMN IF NOT EXISTS last_name TEXT NOT NULL DEFAULT '';

-- Add image to menu_item
ALTER TABLE menu_item ADD COLUMN IF NOT EXISTS image_id INTEGER DEFAULT null;
ALTER TABLE menu_item DROP CONSTRAINT IF EXISTS menu_item_image_id_fkey;
ALTER TABLE menu_item ADD FOREIGN KEY (image_id) REFERENCES image(id) ON DELETE SET null;
