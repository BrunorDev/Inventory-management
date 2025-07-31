package com.example.inventory_api.dtos.categoryDTO;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank
        String name
) {
}
