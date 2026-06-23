package com.kharchoufi.warehouse_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WarehouseRequest(

        @NotBlank(message = "Code is required")
        @Size(max = 32, message = "Code must be at most 32 characters")
        String code,

        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be at most 255 characters")
        String name,

        String city
) {}