-- liquibase formatted sql

-- changeset filip:11
ALTER TABLE category
    ADD UNIQUE (name);

-- changeset filip:12
CREATE TABLE sales_order
(
    id          INT AUTO_INCREMENT NOT NULL,
    order_date  DATETIME           NOT NULL,
    description VARCHAR(255)       NULL,
    CONSTRAINT PK_SALES_ORDER PRIMARY KEY (id)
);

-- changeset filip:13
ALTER TABLE pancake
    ADD sales_order_id INT NULL;

-- changeset filip:16
ALTER TABLE pancake
    ADD CONSTRAINT fk_pancake_sales_order FOREIGN KEY (sales_order_id) REFERENCES sales_order (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset filip:17
CREATE INDEX fk_pancakes_sales_order_idx ON pancake (sales_order_id);

-- changeset filip:18
ALTER TABLE ingredient
    ADD is_healthy TINYINT;

-- changeset filip:19
Alter TABLE pancake MODIFY sales_order_id INT NOT NULL;

-- changeset filip:20
ALTER TABLE pancake
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (id, sales_order_id);
