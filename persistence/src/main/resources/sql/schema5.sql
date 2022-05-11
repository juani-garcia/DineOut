-- Add menu section ordering maintenance on insert
-- CREATE OR REPLACE FUNCTION maintain_ordering_menusection() RETURNS trigger AS $maintain_ordering_menusection$
CREATE OR REPLACE FUNCTION maintain_ordering_menusection() RETURNS trigger AS '
DECLARE
    query_cursor CURSOR FOR SELECT *
                            FROM menu_section
                            WHERE ordering >= NEW.ordering AND restaurant_id = NEW.restaurant_id
                            ORDER BY ordering DESC
                            FOR UPDATE;
    rec RECORD;
BEGIN
    OPEN query_cursor;
    FETCH query_cursor INTO rec;    -- Read a row from the cursor
    WHILE FOUND LOOP
        UPDATE menu_section SET ordering = ordering + 1 WHERE CURRENT OF query_cursor;
        FETCH query_cursor INTO rec;  -- Keep on reading rows
    END LOOP;
    CLOSE query_cursor;
    RETURN NEW;
END;
' LANGUAGE plpgsql;
-- $maintain_ordering_menusection$' LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS dineout_menusection_ordering ON menu_section;

CREATE TRIGGER dineout_menusection_ordering
    BEFORE INSERT ON menu_section
    FOR EACH ROW
    EXECUTE PROCEDURE maintain_ordering_menusection();

-- Add menu item ordering maintenance on insert
-- CREATE OR REPLACE FUNCTION maintain_ordering_menuitem() RETURNS trigger AS $maintain_ordering_menuitem$
CREATE OR REPLACE FUNCTION maintain_ordering_menuitem() RETURNS trigger AS '
DECLARE
    query_cursor CURSOR FOR SELECT *
                            FROM menu_item
                            WHERE ordering >= NEW.ordering AND section_id = NEW.section_id
                            ORDER BY ordering DESC
                            FOR UPDATE;
    rec RECORD;
BEGIN
    OPEN query_cursor;
    FETCH query_cursor INTO rec;    -- Read a row from the cursor
    WHILE FOUND LOOP
            UPDATE menu_item SET ordering = ordering + 1 WHERE CURRENT OF query_cursor;
            FETCH query_cursor INTO rec;  -- Keep on reading rows
        END LOOP;
    CLOSE query_cursor;
    RETURN NEW;
END;
' LANGUAGE plpgsql;
-- $maintain_ordering_menuitem$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS dineout_menuitem_ordering ON menu_item;

CREATE TRIGGER dineout_menuitem_ordering
    BEFORE INSERT ON menu_item
    FOR EACH ROW
EXECUTE PROCEDURE maintain_ordering_menuitem();
