package com.kharchoufi.warehouse_api.dto;

public record StockRequest(
        Long productId,
        Long warehouseId,
        int quantity
) {}