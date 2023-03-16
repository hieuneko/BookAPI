DELETE FROM roles WHERE name = 'ADMIN';
DELETE FROM roles WHERE name = 'CONTRIBUTOR';
DELETE FROM users WHERE username = 'admin';

INSERT INTO roles(id, name) VALUES ('921210b1-dfe2-425a-8480-01f6f01be15e', 'ADMIN'),
                                   ('8b929657-6884-48b5-a25a-5b223acbdfcf', 'CONTRIBUTOR');

INSERT INTO users(username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('admin', 'OHjcWdaFE35A7kBJBmVyjlNfaJ8lWgHI2zaUR4tjGhI=', 'hieu', 'pham', true, null , '921210b1-dfe2-425a-8480-01f6f01be15e')