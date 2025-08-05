package com.example.inventory_api.dtos.userDTO;

import com.example.inventory_api.domain.enums.Profile;

public record UserResponse(
        Long id,
        String name,
        String email,
        Profile profile
) {
}
