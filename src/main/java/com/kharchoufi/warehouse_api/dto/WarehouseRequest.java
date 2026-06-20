package com.kharchoufi.warehouse_api.dto;

public record WarehouseRequest(
        String code,
        String name,
        String city
) {}