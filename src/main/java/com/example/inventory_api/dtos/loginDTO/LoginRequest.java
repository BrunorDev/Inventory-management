package com.example.inventory_api.dtos.loginDTO;

public record LoginRequest(
        String email,
        String password
) {
}
