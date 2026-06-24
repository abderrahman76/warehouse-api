package com.kharchoufi.warehouse_api.dto;

import com.kharchoufi.warehouse_api.model.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        Role role,
        LocalDateTime createdAt
) {}