package com.kharchoufi.warehouse_api.dto;

public record ProductRequest (
    String sku,
    String name,
    String description
){}
