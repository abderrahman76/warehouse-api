CREATE TABLE stock (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       product_id BIGINT NOT NULL,
                       warehouse_id BIGINT NOT NULL,
                       quantity INT NOT NULL DEFAULT 0,
                       CONSTRAINT fk_stock_product FOREIGN KEY (product_id) REFERENCES products(id),
                       CONSTRAINT fk_stock_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(id),
                       CONSTRAINT uq_stock_product_warehouse UNIQUE (product_id, warehouse_id)
);