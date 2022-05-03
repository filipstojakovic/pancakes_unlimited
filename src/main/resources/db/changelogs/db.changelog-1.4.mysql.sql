-- liquibase formatted sql

-- changeset filip:22
DROP TABLE order_has_pancake;

ALTER TABLE pancake
    ADD sales_order_id INT NULL;

ALTER TABLE pancake
    ADD CONSTRAINT fk_pancake_sales_order FOREIGN KEY (sales_order_id) REFERENCES sales_order (id) ON DELETE CASCADE;

-- changeset filip:23
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
