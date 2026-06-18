package com.kharchoufi.warehouse_api.dto;

import java.time.LocalDateTime;

public record ProductResponse (
        Long id,
        String sku,
        String name,
        String description,
        LocalDateTime createdAt
){}
