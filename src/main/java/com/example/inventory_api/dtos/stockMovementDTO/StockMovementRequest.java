package com.example.inventory_api.dtos.stockMovementDTO;

import com.example.inventory_api.domain.enums.Type;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockMovementRequest(
        @NotNull
        Long productId,

        @NotNull
        Long userId,

        @NotNull
        Type type,

        @NotNull
        @Positive
        @Min(1)
        Integer quantity,

        String note
) {
}
