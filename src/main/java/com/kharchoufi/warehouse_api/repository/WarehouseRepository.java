package com.kharchoufi.warehouse_api.repository;

import com.kharchoufi.warehouse_api.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    boolean existsByCode(String code);
}