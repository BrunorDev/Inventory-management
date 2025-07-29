package com.example.inventory_api.dtos.userDTO;

import com.example.inventory_api.domain.enums.Profile;

public record UserRequest(
        String name,
        String email,
        String password,
        Profile profile
) {
}
