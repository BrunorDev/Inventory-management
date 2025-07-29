package com.example.inventory_api.dtos.userDTO;

import com.example.inventory_api.domain.enums.Profile;

public record UserResponse(
        String name,
        String email,
        Profile profile
) {
}
