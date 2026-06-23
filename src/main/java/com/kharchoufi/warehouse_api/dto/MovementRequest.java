package com.kharchoufi.warehouse_api.dto;

import com.kharchoufi.warehouse_api.model.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovementRequest(

        @NotNull(message = "productId is required")
        Long productId,

        @NotNull(message = "warehouseId is required")
        Long warehouseId,

        @NotNull(message = "type is required (INBOUND or OUTBOUND)")
        MovementType type,

        @Positive(message = "Quantity must be greater than zero")
        int quantity
) {}