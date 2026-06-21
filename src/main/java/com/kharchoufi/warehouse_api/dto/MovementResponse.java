package com.kharchoufi.warehouse_api.dto;

import com.kharchoufi.warehouse_api.model.MovementType;

import java.time.LocalDateTime;

public record MovementResponse(
        Long id,
        ProductResponse product,
        WarehouseResponse warehouse,
        MovementType type,
        int quantity,
        LocalDateTime createdAt
) {}