CREATE TABLE warehouses (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            code VARCHAR(32) NOT NULL UNIQUE,
                            name VARCHAR(255) NOT NULL,
                            city VARCHAR(128),
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);