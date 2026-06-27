package com.kharchoufi.warehouse_api.controller;

import com.kharchoufi.warehouse_api.dto.AuthResponse;
import com.kharchoufi.warehouse_api.dto.LoginRequest;
import com.kharchoufi.warehouse_api.dto.RegisterRequest;
import com.kharchoufi.warehouse_api.dto.UserResponse;
import com.kharchoufi.warehouse_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}