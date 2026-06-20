package com.kharchoufi.warehouse_api.repository;

import com.kharchoufi.warehouse_api.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByWarehouseId(Long warehouseId);

    List<Stock> findByProductId(Long productId);

    Optional<Stock> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    boolean existsByProductIdAndWarehouseId(Long productId, Long warehouseId);
}