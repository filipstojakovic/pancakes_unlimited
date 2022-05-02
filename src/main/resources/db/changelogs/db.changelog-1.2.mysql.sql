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

-- changeset filip:18
ALTER TABLE ingredient
    MODIFY COLUMN is_healthy BIT(1) NOT NULL;

-- changeset filip:19
ALTER TABLE pancake_has_ingredient
    DROP FOREIGN KEY fk_pancake_has_ingredient_ingredient;

ALTER TABLE pancake_has_ingredient
    ADD CONSTRAINT fk_pancake_has_ingredient_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient (id) ON DELETE CASCADE;

ALTER TABLE pancake_has_ingredient
    DROP FOREIGN KEY fk_pancake_has_ingredient_pancake;

ALTER TABLE pancake_has_ingredient
    ADD CONSTRAINT fk_pancake_has_ingredient_pancake FOREIGN KEY (pancake_id) REFERENCES pancake (id) ON DELETE CASCADE;

-- changeset filip:20
ALTER TABLE order_has_pancake
    DROP FOREIGN KEY fk_oder_has_pancake_sales_order;

ALTER TABLE order_has_pancake
    ADD CONSTRAINT fk_oder_has_pancake_sales_order FOREIGN KEY (sales_order_id) REFERENCES sales_order (id) ON DELETE CASCADE;;

ALTER TABLE order_has_pancake
    DROP FOREIGN KEY fk_oder_has_pancake_pancake;

ALTER TABLE order_has_pancake
    ADD CONSTRAINT fk_oder_has_pancake_pancake FOREIGN KEY (pancake_id) REFERENCES pancake (id) ON DELETE CASCADE;

-- changeset filip:21
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));