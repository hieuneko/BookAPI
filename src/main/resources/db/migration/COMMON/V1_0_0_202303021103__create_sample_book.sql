INSERT INTO books(title, author, description, user_id)
VALUES ('Harry Potter','JK. Rolling', 'Kha la hay', (SELECT id FROM users WHERE username = 'admin')),
       ('Diary of a Minecraft Zombie','Zack Zombie', 'kinda suck', (SELECT id FROM users WHERE username = 'admin'))