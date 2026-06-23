package com.kharchoufi.warehouse_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockRequest(

        @NotNull(message = "productId is required")
        Long productId,

        @NotNull(message = "warehouseId is required")
        Long warehouseId,

        @PositiveOrZero(message = "Initial quantity cannot be negative")
        int quantity
) {}