-- liquibase formatted sql

-- changeset filip:11
ALTER TABLE category
    ADD UNIQUE (name);