CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id        UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    username  VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    enabled   BOOLEAN      NOT NULL,
    avatar    VARCHAR(255),
    role_id   UUID         NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO users(username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('admin', 'OHjcWdaFE35A7kBJBmVyjlNfaJ8lWgHI2zaUR4tjGhI=', 'hieu', 'pham', true, null , (SELECT id FROM roles WHERE name = 'ADMIN'))

