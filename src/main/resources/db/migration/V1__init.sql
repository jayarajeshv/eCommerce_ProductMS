CREATE TABLE categories
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NULL,
    modified_at datetime NULL,
    title       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE products
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime NULL,
    modified_at   datetime NULL,
    title         VARCHAR(255) NULL,
    price DOUBLE NULL,
    `description` VARCHAR(255) NULL,
    category_id   BIGINT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE categories
    ADD CONSTRAINT uc_categories_title UNIQUE (title);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);