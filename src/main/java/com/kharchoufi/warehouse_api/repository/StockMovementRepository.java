package com.kharchoufi.warehouse_api.repository;

import com.kharchoufi.warehouse_api.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
}