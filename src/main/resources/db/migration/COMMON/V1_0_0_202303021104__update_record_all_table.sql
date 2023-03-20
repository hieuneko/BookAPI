DELETE
FROM books;

DELETE
FROM users;

DELETE
FROM roles;

INSERT INTO roles(id, name)
VALUES ('921210b1-dfe2-425a-8480-01f6f01be15e', 'ADMIN'),
       ('8b929657-6884-48b5-a25a-5b223acbdfcf', 'CONTRIBUTOR');

INSERT INTO users(username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('admin', '$2a$12$vNpG4tP2u6qIjAfgZq87G.NfJHZIIPTNvw4DGEBHr6zKY3t8soVTC', 'hieu', 'pham', true, null,
        '921210b1-dfe2-425a-8480-01f6f01be15e');

INSERT INTO users(username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('user', '$2a$12$uap.7jv3yQ3g7KgqbSQOfObjyjs39PGTmDJaOsnyMlXPsv8W5K5MO', 'test', 'test', true, null,
        '8b929657-6884-48b5-a25a-5b223acbdfcf');

INSERT INTO books(title, author, description, user_id)
VALUES ('Harry Potter', 'JK. Rolling', 'Kha la hay', (SELECT id FROM users WHERE username = 'admin')),
       ('Diary of a Minecraft Zombie', 'Zack Zombie', 'kinda suck', (SELECT id FROM users WHERE username = 'admin'))