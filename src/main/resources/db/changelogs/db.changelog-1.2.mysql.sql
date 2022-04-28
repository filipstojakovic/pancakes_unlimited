-- liquibase formatted sql

-- changeset filip:15
INSERT INTO category (name)
VALUES ('baza');
INSERT INTO category (name)
VALUES ('fil');
INSERT INTO category (name)
VALUES ('preliv');
INSERT INTO category (name)
VALUES ('voće');


-- changeset filip:16
ALTER TABLE sales_order
    ADD order_number VARCHAR(255) NOT NULL;

-- changeset filip:17
ALTER TABLE sales_order
    MODIFY COLUMN order_number CHAR(36) NOT NULL;