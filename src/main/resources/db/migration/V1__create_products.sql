CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          sku VARCHAR(64) NOT NULL UNIQUE,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);