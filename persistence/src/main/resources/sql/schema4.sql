-- Add first and last name to account.
ALTER TABLE account ADD COLUMN first_name TEXT NOT NULL DEFAULT '';
ALTER TABLE account ADD COLUMN last_name TEXT NOT NULL DEFAULT '';

-- Add image to menu_item
ALTER TABLE menu_item ADD COLUMN image_id INTEGER DEFAULT null;
ALTER TABLE menu_item ADD FOREIGN KEY (image_id) REFERENCES image(id) ON DELETE SET null;
