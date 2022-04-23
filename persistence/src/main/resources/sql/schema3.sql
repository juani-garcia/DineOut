CREATE TABLE IF NOT EXISTS account_role (
                                            id SERIAL PRIMARY KEY,
                                            role_name TEXT NOT NULL,
                                            unique(role_name)
    );

CREATE TABLE IF NOT EXISTS account_to_role (
                                               id SERIAL PRIMARY KEY,
                                               user_id INTEGER NOT NULL,
                                               role_id INTEGER NOT NULL,
                                               FOREIGN KEY (user_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES account_role(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS role_authorities (
                                                id SERIAL PRIMARY KEY,
                                                authority_name TEXT NOT NULL,
                                                unique(authority_name)
    );

CREATE TABLE IF NOT EXISTS role_to_authority (
                                                 id SERIAL PRIMARY KEY,
                                                 authority_id INTEGER NOT NULL,
                                                 role_id INTEGER NOT NULL,
                                                 FOREIGN KEY (authority_id) REFERENCES role_authorities(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES account_role(id) ON DELETE CASCADE
    );

INSERT INTO account_role(id, role_name) SELECT 1, 'ADMIN' WHERE NOT EXISTS (SELECT id FROM account_role WHERE role_name='ADMIN') RETURNING id;
INSERT INTO account_role(id, role_name) SELECT 2, 'RESTAURANT' WHERE NOT EXISTS (SELECT id FROM account_role WHERE role_name='RESTAURANT') RETURNING id;
INSERT INTO account_role(id, role_name) SELECT 3, 'DINER' WHERE NOT EXISTS (SELECT id FROM account_role WHERE role_name='DINER') RETURNING id;
INSERT INTO account_role(id, role_name) SELECT 4, 'BASIC_USER' WHERE NOT EXISTS (SELECT id FROM account_role WHERE role_name='BASIC_USER') RETURNING id;

INSERT INTO role_authorities(id, authority_name) SELECT 1, 'canCreateRestaurant' WHERE NOT EXISTS (SELECT id FROM role_authorities WHERE authority_name='canCreateRestaurant') RETURNING id;
INSERT INTO role_authorities(id, authority_name) SELECT 2, 'canEditProfile' WHERE NOT EXISTS (SELECT id FROM role_authorities WHERE authority_name='canEditProfile') RETURNING id;
INSERT INTO role_authorities(id, authority_name) SELECT 3, 'canReserveTable' WHERE NOT EXISTS (SELECT id FROM role_authorities WHERE authority_name='canReserveTable') RETURNING id;
INSERT INTO role_authorities(id, authority_name) SELECT 4, 'canEditMenu' WHERE NOT EXISTS (SELECT id FROM role_authorities WHERE authority_name='canEditMenu') RETURNING id;
INSERT INTO role_authorities(id, authority_name) SELECT 5, 'canViewRestaurant' WHERE NOT EXISTS (SELECT id FROM role_authorities WHERE authority_name='canViewRestaurant') RETURNING id;
INSERT INTO role_authorities(id, authority_name) SELECT 6, 'canEditRestaurantDetails' WHERE NOT EXISTS (SELECT id FROM role_authorities WHERE authority_name='canEditRestaurantDetails') RETURNING id;

INSERT INTO role_to_authority(id, role_id, authority_id) SELECT 1, 2, 1 WHERE NOT EXISTS (SELECT id FROM role_to_authority WHERE role_id=2 AND authority_id=1) RETURNING id; -- Role RESTAURANT canCreateRestaurant
INSERT INTO role_to_authority(id, role_id, authority_id) SELECT 2, 2, 4 WHERE NOT EXISTS (SELECT id FROM role_to_authority WHERE role_id=2 AND authority_id=4) RETURNING id; -- Role RESTAURANT canEditMenu
INSERT INTO role_to_authority(id, role_id, authority_id) SELECT 3, 2, 6 WHERE NOT EXISTS (SELECT id FROM role_to_authority WHERE role_id=2 AND authority_id=6) RETURNING id; -- Role RESTAURANT canEditRestaurantDetails

INSERT INTO role_to_authority(id, role_id, authority_id) SELECT 4, 3, 3 WHERE NOT EXISTS (SELECT id FROM role_to_authority WHERE role_id=3 AND authority_id=3) RETURNING id; -- Role DINER canReserveTable

INSERT INTO role_to_authority(id, role_id, authority_id) SELECT 5, 4, 2 WHERE NOT EXISTS (SELECT id FROM role_to_authority WHERE role_id=4 AND authority_id=2) RETURNING id; -- Role BASIC_USER canEditUserDetails
INSERT INTO role_to_authority(id, role_id, authority_id) SELECT 6, 4, 5 WHERE NOT EXISTS (SELECT id FROM role_to_authority WHERE role_id=4 AND authority_id=5) RETURNING id; -- Role BASIC_USER canViewRestaurant
