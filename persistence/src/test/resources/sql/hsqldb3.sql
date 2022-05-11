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

INSERT INTO account_role(id, role_name) VALUES(1, 'ADMIN');
INSERT INTO account_role(id, role_name) VALUES(2, 'RESTAURANT');
INSERT INTO account_role(id, role_name) VALUES(3, 'DINER');
INSERT INTO account_role(id, role_name) VALUES(4, 'BASIC_USER');

INSERT INTO role_authorities(id, authority_name) VALUES(1, 'canCreateRestaurant');
INSERT INTO role_authorities(id, authority_name) VALUES(2, 'canEditProfile');
INSERT INTO role_authorities(id, authority_name) VALUES(3, 'canReserveTable');
INSERT INTO role_authorities(id, authority_name) VALUES(4, 'canEditMenu');
INSERT INTO role_authorities(id, authority_name) VALUES(5, 'canViewRestaurant');
INSERT INTO role_authorities(id, authority_name) VALUES(6, 'canEditRestaurantDetails');

INSERT INTO role_to_authority(id, role_id, authority_id) VALUES(1, 2, 1); -- Role RESTAURANT canCreateRestaurant
INSERT INTO role_to_authority(id, role_id, authority_id) VALUES(2, 2, 4); -- Role RESTAURANT canEditMenu
INSERT INTO role_to_authority(id, role_id, authority_id) VALUES(3, 2, 6); -- Role RESTAURANT canEditRestaurantDetails

INSERT INTO role_to_authority(id, role_id, authority_id) VALUES(4, 3, 3); -- Role DINER canReserveTable

INSERT INTO role_to_authority(id, role_id, authority_id) VALUES(5, 4, 2); -- Role BASIC_USER canEditUserDetails
INSERT INTO role_to_authority(id, role_id, authority_id) VALUES(6, 4, 5); -- Role BASIC_USER canViewRestaurant
