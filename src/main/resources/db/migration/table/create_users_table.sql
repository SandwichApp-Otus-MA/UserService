--liquibase formatted sql
--changeset AVoronov:v1.0.0/users

CREATE TABLE IF NOT EXISTS users
(
    id uuid NOT NULL PRIMARY KEY,
    last_name varchar NOT NULL,
    first_name varchar NOT NULL,
    middle_name varchar,
    birth_date date,
    email varchar,
    phone_number varchar,
    status varchar
);