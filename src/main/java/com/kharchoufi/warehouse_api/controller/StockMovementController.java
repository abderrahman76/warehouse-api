package com.kharchoufi.warehouse_api.controller;

import com.kharchoufi.warehouse_api.dto.MovementRequest;
import com.kharchoufi.warehouse_api.dto.MovementResponse;
import com.kharchoufi.warehouse_api.service.StockMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movements")
public class StockMovementController {

    private final StockMovementService movementService;

    public StockMovementController(StockMovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovementResponse record(@RequestBody MovementRequest request) {
        return movementService.record(request);
    }
}