--liquibase formatted sql
--changeset AVoronov:v1.0.0/users

CREATE TABLE IF NOT EXISTS users
(
    id uuid NOT NULL PRIMARY KEY,
    last_name varchar NOT NULL,
    first_name varchar NOT NULL,
    middle_name varchar,
    birth_date date,
    email varchar UNIQUE ,
    password text,
    phone_number varchar UNIQUE,
    status varchar,
    roles varchar[] NOT NULL
);

CREATE INDEX idx_users_phone_number ON users (phone_number);
CREATE INDEX idx_users_email ON users (email);