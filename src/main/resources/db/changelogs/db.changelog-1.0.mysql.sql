-- liquibase formatted sql

-- changeset filip:1
CREATE TABLE category
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)       NOT NULL,
    CONSTRAINT PK_CATEGORY PRIMARY KEY (id)
);

-- changeset filip:2
CREATE TABLE ingredient
(
    id          INT AUTO_INCREMENT NOT NULL,
    category_id INT                NOT NULL,
    price       DECIMAL(10, 2)     NOT NULL,
    name        VARCHAR(255)       NOT NULL,
    CONSTRAINT PK_INGREDIENT PRIMARY KEY (id, category_id)
);

-- changeset filip:3
CREATE TABLE pancake
(
    id INT AUTO_INCREMENT NOT NULL,
    CONSTRAINT PK_PANCAKE PRIMARY KEY (id)
);

-- changeset filip:4
CREATE TABLE pancake_has_ingredient
(
    pancake_id    INT            NOT NULL,
    ingredient_id INT            NOT NULL,
    price         DECIMAL(10, 2) NOT NULL,
    CONSTRAINT PK_PANCAKE_HAS_INGREDIENT PRIMARY KEY (pancake_id, ingredient_id)
);

-- changeset filip:5
CREATE INDEX fk_ingredient_category_idx ON ingredient (category_id);

-- changeset filip:6
CREATE INDEX fk_pancake_has_ingredient_ingredient_idx ON pancake_has_ingredient (ingredient_id);

-- changeset filip:7
CREATE INDEX fk_pancake_has_ingredient_pancake_idx ON pancake_has_ingredient (pancake_id);

-- changeset filip:8
ALTER TABLE ingredient
    ADD CONSTRAINT fk_ingredient_category FOREIGN KEY (category_id) REFERENCES category (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset filip:9
ALTER TABLE pancake_has_ingredient
    ADD CONSTRAINT fk_pancake_has_ingredient_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset filip:10
ALTER TABLE pancake_has_ingredient
    ADD CONSTRAINT fk_pancake_has_ingredient_pancake FOREIGN KEY (pancake_id) REFERENCES pancake (id) ON UPDATE RESTRICT ON DELETE RESTRICT;