ALTER TABLE restaurant ADD COLUMN image_id INTEGER;
ALTER TABLE restaurant ADD FOREIGN KEY (image_id) REFERENCES image (id) ON DELETE SET NULL;
