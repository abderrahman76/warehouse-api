package com.kharchoufi.warehouse_api.service;

import com.kharchoufi.warehouse_api.dto.MovementRequest;
import com.kharchoufi.warehouse_api.exception.InsufficientStockException;
import com.kharchoufi.warehouse_api.model.*;
import com.kharchoufi.warehouse_api.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockMovementServiceTest {

    @Mock private StockMovementRepository movementRepository;
    @Mock private StockRepository stockRepository;
    @Mock private ProductRepository productRepository;
    @Mock private WarehouseRepository warehouseRepository;

    @InjectMocks private StockMovementService movementService;

    @Test
    void outbound_throwsInsufficientStock_andWritesNothing_whenNotEnoughAvailable() {
        // Arrange: a stock row with only 30 units
        Product product = new Product();
        product.setId(1L);
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setWarehouse(warehouse);
        stock.setQuantity(30);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(stockRepository.findByProductIdAndWarehouseIdForUpdate(1L, 1L))
                .thenReturn(Optional.of(stock));

        // Act: try to remove 50 — more than the 30 available
        MovementRequest request = new MovementRequest(1L, 1L, MovementType.OUTBOUND, 50);

        // Assert: it's rejected, AND nothing was written to the database
        assertThatThrownBy(() -> movementService.record(request))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("only 30 available");

        verify(movementRepository, never()).save(any());
        verify(stockRepository, never()).save(any());
    }
}