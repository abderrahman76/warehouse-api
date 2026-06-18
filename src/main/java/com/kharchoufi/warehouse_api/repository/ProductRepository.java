package com.kharchoufi.warehouse_api.repository;

import com.kharchoufi.warehouse_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);
}
