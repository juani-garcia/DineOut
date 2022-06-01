DROP TRIGGER IF EXISTS update_item_section_trigger ON menu_section;
DROP TRIGGER IF EXISTS insert_item_section_trigger ON menu_section;
DROP TRIGGER IF EXISTS delete_item_section_trigger ON menu_section;
DROP TRIGGER IF EXISTS update_item_trigger ON menu_item;
DROP TRIGGER IF EXISTS insert_item_trigger ON menu_item;
DROP TRIGGER IF EXISTS delete_item_trigger ON menu_item;
ALTER TABLE menu_item DROP CONSTRAINT menu_item_ordering_unique;
ALTER TABLE menu_section DROP CONSTRAINT menu_section_ordering_unique;