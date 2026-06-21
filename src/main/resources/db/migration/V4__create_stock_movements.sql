CREATE TABLE stock_movements (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 product_id BIGINT NOT NULL,
                                 warehouse_id BIGINT NOT NULL,
                                 type VARCHAR(16) NOT NULL,
                                 quantity INT NOT NULL,
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 CONSTRAINT fk_movement_product FOREIGN KEY (product_id) REFERENCES products(id),
                                 CONSTRAINT fk_movement_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(id)
);