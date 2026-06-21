package com.kharchoufi.warehouse_api.repository;

import com.kharchoufi.warehouse_api.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findAllByOrderByCreatedAtDesc();

    List<StockMovement> findByProductIdOrderByCreatedAtDesc(Long productId);
}