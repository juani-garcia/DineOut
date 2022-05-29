-- Add first and last name to account.

-- Add image to menu_item
ALTER TABLE menu_item ADD COLUMN image_id INTEGER DEFAULT null;
ALTER TABLE menu_item ADD FOREIGN KEY (image_id) REFERENCES image(id) ON DELETE CASCADE;
