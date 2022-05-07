-- liquibase formatted sql

-- changeset filip:23
ALTER TABLE ingredient
    ADD created_at DATETIME,
    ADD created_by VARCHAR(255),
    ADD updated_at DATETIME,
    ADD updated_by VARCHAR(255);

ALTER TABLE pancake
    ADD created_at DATETIME,
    ADD created_by VARCHAR(255),
    ADD updated_at DATETIME,
    ADD updated_by VARCHAR(255);

ALTER TABLE sales_order
    ADD created_at DATETIME,
    ADD created_by VARCHAR(255);

-- changeset filip:24
ALTER TABLE sales_order
    MODIFY order_date DATETIME NOT NULL;

-- changeset filip:25
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
