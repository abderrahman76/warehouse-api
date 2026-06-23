package com.kharchoufi.warehouse_api.service;

import com.kharchoufi.warehouse_api.dto.MovementRequest;
import com.kharchoufi.warehouse_api.dto.MovementResponse;
import com.kharchoufi.warehouse_api.exception.InsufficientStockException;
import com.kharchoufi.warehouse_api.mapper.MovementMapper;
import com.kharchoufi.warehouse_api.model.*;
import com.kharchoufi.warehouse_api.repository.ProductRepository;
import com.kharchoufi.warehouse_api.repository.StockMovementRepository;
import com.kharchoufi.warehouse_api.repository.StockRepository;
import com.kharchoufi.warehouse_api.repository.WarehouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StockMovementService {

    private final StockMovementRepository movementRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public StockMovementService(StockMovementRepository movementRepository,
                                StockRepository stockRepository,
                                ProductRepository productRepository,
                                WarehouseRepository warehouseRepository) {
        this.movementRepository = movementRepository;
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public MovementResponse record(MovementRequest request) {
        if (request.quantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Movement quantity must be greater than zero");
        }

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found with id " + request.productId()));

        Warehouse warehouse = warehouseRepository.findById(request.warehouseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Warehouse not found with id " + request.warehouseId()));

        Stock stock = stockRepository
                .findByProductIdAndWarehouseIdForUpdate(request.productId(), request.warehouseId())
                .orElse(null);

        if (request.type() == MovementType.INBOUND) {
            // Receiving goods. If this product has never been stocked here, start a new row.
            if (stock == null) {
                stock = new Stock();
                stock.setProduct(product);
                stock.setWarehouse(warehouse);
                stock.setQuantity(0);
            }
            stock.setQuantity(stock.getQuantity() + request.quantity());
        } else {
            // OUTBOUND — picking goods. This is the rule that matters.
            int available = (stock == null) ? 0 : stock.getQuantity();
            if (available < request.quantity()) {
                throw new InsufficientStockException(
                        "Cannot remove " + request.quantity() + " units; only "
                                + available + " available in this warehouse");
            }
            stock.setQuantity(stock.getQuantity() - request.quantity());
        }

        stockRepository.save(stock);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setWarehouse(warehouse);
        movement.setType(request.type());
        movement.setQuantity(request.quantity());
        StockMovement saved = movementRepository.save(movement);

        return MovementMapper.toResponse(saved);
    }

    public List<MovementResponse> findHistory(Long productId) {
        List<StockMovement> movements = (productId == null)
                ? movementRepository.findAllByOrderByCreatedAtDesc()
                : movementRepository.findByProductIdOrderByCreatedAtDesc(productId);

        return movements.stream()
                .map(MovementMapper::toResponse)
                .toList();
    }
}
