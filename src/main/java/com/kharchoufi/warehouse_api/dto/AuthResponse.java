package com.kharchoufi.warehouse_api.dto;

import com.kharchoufi.warehouse_api.model.Role;

public record AuthResponse(
        String token,
        String username,
        Role role
) {}