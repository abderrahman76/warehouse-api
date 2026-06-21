package com.kharchoufi.warehouse_api.dto;

import com.kharchoufi.warehouse_api.model.MovementType;

public record MovementRequest(
        Long productId,
        Long warehouseId,
        MovementType type,
        int quantity
) {}