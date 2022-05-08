DROP TRIGGER IF EXISTS dineout_menusection_ordering ON menu_section;
DROP FUNCTION IF EXISTS maintain_order_in_menusection();
DROP TRIGGER IF EXISTS dineout_menuitem_ordering ON menu_item;
DROP FUNCTION IF EXISTS maintain_order_in_menuitem();

ALTER TABLE menu_section DROP CONSTRAINT IF EXISTS menu_section_ordering_restaurant_id_key;
ALTER TABLE menu_section DROP CONSTRAINT IF EXISTS menu_section_ordering_unique;
ALTER TABLE menu_section ADD CONSTRAINT menu_section_ordering_unique UNIQUE(restaurant_id, ordering) DEFERRABLE INITIALLY DEFERRED;

-- Automatically order into last place for new sections
CREATE OR REPLACE FUNCTION insert_section_ordering()
    RETURNS TRIGGER AS $$
BEGIN
    SELECT COUNT(*) + 1 INTO NEW.ordering FROM menu_section WHERE restaurant_id = NEW.restaurant_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS insert_item_section_trigger ON menu_section;
CREATE TRIGGER insert_item_section_trigger
    BEFORE INSERT on menu_section
    FOR EACH ROW
EXECUTE PROCEDURE insert_section_ordering();

-- Automatically rearrangement of orders when deletion
CREATE OR REPLACE FUNCTION delete_section_ordering()
    RETURNS TRIGGER AS $$
DECLARE
    cursor CURSOR FOR SELECT *
                      FROM menu_section
                      WHERE menu_section.restaurant_id = OLD.restaurant_id AND ordering >= OLD.ordering
                      ORDER BY ordering
                          FOR UPDATE;
    rec RECORD;
BEGIN
    OPEN cursor;
    FETCH cursor INTO rec;
    WHILE FOUND LOOP
            UPDATE menu_section SET ordering = ordering - 1 WHERE CURRENT OF cursor;
            FETCH cursor INTO rec;
        END LOOP;
    CLOSE cursor;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS delete_item_section_trigger ON menu_section;
CREATE TRIGGER delete_item_section_trigger
    AFTER DELETE on menu_section
    FOR EACH ROW
EXECUTE PROCEDURE delete_section_ordering();

-- Automatically swap orders
CREATE OR REPLACE FUNCTION update_section_ordering()
    RETURNS TRIGGER AS $$
DECLARE
    cursor CURSOR FOR SELECT *
                      FROM menu_section
                      WHERE menu_section.restaurant_id = OLD.restaurant_id AND ordering = NEW.ordering;
    rec RECORD;
BEGIN
    IF OLD.ordering != NEW.ordering THEN
        UPDATE menu_section SET ordering = OLD.ordering WHERE restaurant_id = OLD.restaurant_id AND ordering = NEW.ordering;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS update_item_section_trigger ON menu_section;
CREATE TRIGGER update_item_section_trigger
    BEFORE UPDATE on menu_section
    FOR EACH ROW
    WHEN (pg_trigger_depth() < 1)
EXECUTE PROCEDURE update_section_ordering();

-- ITEM
ALTER TABLE menu_item DROP CONSTRAINT IF EXISTS menu_item_ordering_section_id_key;
ALTER TABLE menu_item DROP CONSTRAINT IF EXISTS menu_item_ordering_unique;
ALTER TABLE menu_item ADD CONSTRAINT menu_item_ordering_unique UNIQUE(section_id, ordering) DEFERRABLE INITIALLY DEFERRED;

-- Automatically order into last place for new sections
CREATE OR REPLACE FUNCTION insert_item_ordering()
    RETURNS TRIGGER AS $$
BEGIN
    SELECT COUNT(*) + 1 INTO NEW.ordering FROM menu_item WHERE section_id = NEW.section_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS insert_item_trigger ON menu_item;
CREATE TRIGGER insert_item_trigger
    BEFORE INSERT on menu_item
    FOR EACH ROW
EXECUTE PROCEDURE insert_item_ordering();

-- Automatically rearrangement of orders when deletion
CREATE OR REPLACE FUNCTION delete_item_ordering()
    RETURNS TRIGGER AS $$
DECLARE
    cursor CURSOR FOR SELECT *
                      FROM menu_item
                      WHERE section_id = OLD.section_id AND ordering >= OLD.ordering
                      ORDER BY ordering
                          FOR UPDATE;
    rec RECORD;
BEGIN
    OPEN cursor;
    FETCH cursor INTO rec;
    WHILE FOUND LOOP
            UPDATE menu_item SET ordering = ordering - 1 WHERE CURRENT OF cursor;
            FETCH cursor INTO rec;
        END LOOP;
    CLOSE cursor;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS delete_item_trigger ON menu_item;
CREATE TRIGGER delete_item_trigger
    AFTER DELETE on menu_item
    FOR EACH ROW
EXECUTE PROCEDURE delete_item_ordering();

-- Automatically swap orders
CREATE OR REPLACE FUNCTION update_item_ordering()
    RETURNS TRIGGER AS $$
DECLARE
    cursor CURSOR FOR SELECT *
                      FROM menu_item
                      WHERE section_id = OLD.section_id AND ordering = NEW.ordering;
    rec RECORD;
BEGIN
    IF OLD.ordering != NEW.ordering THEN
        UPDATE menu_item SET ordering = OLD.ordering WHERE section_id = OLD.section_id AND ordering = NEW.ordering;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS update_item_trigger ON menu_item;
CREATE TRIGGER update_item_trigger
    BEFORE UPDATE on menu_item
    FOR EACH ROW
    WHEN (pg_trigger_depth() < 1)
EXECUTE PROCEDURE update_item_ordering();
