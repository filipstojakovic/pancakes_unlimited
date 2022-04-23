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
    Category_id INT                NOT NULL,
    price       DECIMAL(10, 2)     NOT NULL,
    name        VARCHAR(255)       NOT NULL,
    CONSTRAINT PK_INGREDIENT PRIMARY KEY (id, Category_id)
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
    Pancake_id    INT            NOT NULL,
    Ingredient_id INT            NOT NULL,
    price         DECIMAL(10, 2) NOT NULL,
    CONSTRAINT PK_PANCAKE_HAS_INGREDIENT PRIMARY KEY (Pancake_id, Ingredient_id)
);

-- changeset filip:5
CREATE INDEX fk_Ingredient_Category1_idx ON ingredient (Category_id);

-- changeset filip:6
CREATE INDEX fk_Pancake_has_Ingredient_Ingredient1_idx ON pancake_has_ingredient (Ingredient_id);

-- changeset filip:7
CREATE INDEX fk_Pancake_has_Ingredient_Pancake_idx ON pancake_has_ingredient (Pancake_id);

-- changeset filip:8
ALTER TABLE ingredient
    ADD CONSTRAINT fk_Ingredient_Category1 FOREIGN KEY (Category_id) REFERENCES category (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset filip:9
ALTER TABLE pancake_has_ingredient
    ADD CONSTRAINT fk_Pancake_has_Ingredient_Ingredient1 FOREIGN KEY (Ingredient_id) REFERENCES ingredient (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset filip:10
ALTER TABLE pancake_has_ingredient
    ADD CONSTRAINT fk_Pancake_has_Ingredient_Pancake FOREIGN KEY (Pancake_id) REFERENCES pancake (id) ON UPDATE RESTRICT ON DELETE RESTRICT;