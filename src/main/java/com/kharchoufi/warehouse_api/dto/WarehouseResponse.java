package com.kharchoufi.warehouse_api.dto;

import java.time.LocalDateTime;

public record WarehouseResponse(
        Long id,
        String code,
        String name,
        String city,
        LocalDateTime createdAt
) {}