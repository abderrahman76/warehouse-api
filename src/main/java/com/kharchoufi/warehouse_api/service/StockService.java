package com.kharchoufi.warehouse_api.service;

import com.kharchoufi.warehouse_api.dto.StockRequest;
import com.kharchoufi.warehouse_api.dto.StockResponse;
import com.kharchoufi.warehouse_api.mapper.StockMapper;
import com.kharchoufi.warehouse_api.model.Product;
import com.kharchoufi.warehouse_api.model.Stock;
import com.kharchoufi.warehouse_api.model.Warehouse;
import com.kharchoufi.warehouse_api.repository.ProductRepository;
import com.kharchoufi.warehouse_api.repository.StockRepository;
import com.kharchoufi.warehouse_api.repository.WarehouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public StockService(StockRepository stockRepository,
                        ProductRepository productRepository,
                        WarehouseRepository warehouseRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public List<StockResponse> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(StockMapper::toResponse)
                .toList();
    }

    public List<StockResponse> findByWarehouse(Long warehouseId) {
        return stockRepository.findByWarehouseId(warehouseId)
                .stream()
                .map(StockMapper::toResponse)
                .toList();
    }

    public StockResponse register(StockRequest request) {
        if (request.quantity() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Initial quantity cannot be negative");
        }

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found with id " + request.productId()));

        Warehouse warehouse = warehouseRepository.findById(request.warehouseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Warehouse not found with id " + request.warehouseId()));

        if (stockRepository.existsByProductIdAndWarehouseId(request.productId(), request.warehouseId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Stock already exists for this product in this warehouse. Use stock movements to change the quantity.");
        }

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setWarehouse(warehouse);
        stock.setQuantity(request.quantity());

        return StockMapper.toResponse(stockRepository.save(stock));
    }

    public List<StockResponse> findLowStock(int threshold) {
        return stockRepository.findByQuantityLessThan(threshold)
                .stream()
                .map(StockMapper::toResponse)
                .toList();
    }

}