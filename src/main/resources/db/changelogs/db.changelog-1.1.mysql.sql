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
CREATE TABLE order_has_pancake
(
    pancake_id    INT NOT NULL,
    sales_order_id INT NOT NULL,
    quantity      INT NOT NULL DEFAULT 1,
    CONSTRAINT PK_ORDER_HAS_PANCAKE PRIMARY KEY (pancake_id, sales_order_id),
    CONSTRAINT fk_oder_has_pancake_sales_order FOREIGN KEY (sales_order_id) REFERENCES sales_order (id),
    CONSTRAINT fk_oder_has_pancake_pancake FOREIGN KEY (pancake_id) REFERENCES pancake (id)
);

-- changeset filip:14
ALTER TABLE ingredient
    ADD is_healthy TINYINT;