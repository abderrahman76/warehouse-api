package com.kharchoufi.warehouse_api.controller;

import com.kharchoufi.warehouse_api.dto.StockRequest;
import com.kharchoufi.warehouse_api.dto.StockResponse;
import com.kharchoufi.warehouse_api.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<StockResponse> getStock(@RequestParam(required = false) Long warehouseId) {
        return (warehouseId == null)
                ? stockService.findAll()
                : stockService.findByWarehouse(warehouseId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse register(@RequestBody StockRequest request) {
        return stockService.register(request);
    }
}