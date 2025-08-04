package com.example.inventory_api.dtos.stockMovementDTO;

import com.example.inventory_api.domain.enums.Type;

public record StockMovementRequest(
        Long productId,
        Long userId,
        Type type,
        Integer quantity,
        String note
) {
}
