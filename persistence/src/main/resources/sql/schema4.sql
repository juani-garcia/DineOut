-- Add first and last name to account.
ALTER TABLE account ADD COLUMN first_name TEXT NOT NULL DEFAULT '';
ALTER TABLE account ADD COLUMN last_name TEXT NOT NULL DEFAULT '';
