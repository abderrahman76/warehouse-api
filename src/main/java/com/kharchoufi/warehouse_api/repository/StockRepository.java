package com.kharchoufi.warehouse_api.repository;

import com.kharchoufi.warehouse_api.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByWarehouseId(Long warehouseId);

    List<Stock> findByProductId(Long productId);

    Optional<Stock> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    boolean existsByProductIdAndWarehouseId(Long productId, Long warehouseId);

    List<Stock> findByQuantityLessThan(int threshold);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Stock s WHERE s.product.id = :productId AND s.warehouse.id = :warehouseId")
    Optional<Stock> findByProductIdAndWarehouseIdForUpdate(@Param("productId") Long productId,
                                                           @Param("warehouseId") Long warehouseId);
}