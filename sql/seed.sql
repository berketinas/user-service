DROP TABLE IF EXISTS users;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    given_name varchar(64) NOT NULL,
    family_name varchar(64) NOT NULL,
    email varchar(320) UNIQUE NOT NULL,
    country varchar(64)
);

INSERT INTO users (given_name, family_name, email, country)
VALUES ('Berke', 'Tinas', 'berketinas@gmail.com', 'Turkey'),
       ('John', 'Smith', 'johnsmith@gmail.com', 'United Kingdom'),
       ('Jane', 'Smith', 'janesmith@gmail.com', 'United States of America'),
       ('Alex', 'Brown', 'alexbrown@gmail.com', 'Thailand'),
       ('Grace', 'Brown', 'gracebrown@gmail.com', 'Norway'),
       ('Lisa', 'Parker', 'lisaparker@gmail.com', 'Portugal');