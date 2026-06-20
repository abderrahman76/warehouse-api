package com.kharchoufi.warehouse_api.dto;

public record StockResponse(
        Long id,
        ProductResponse product,
        WarehouseResponse warehouse,
        int quantity
) {}