package com.example.inventory_api.dtos.stockMovementDTO;

import com.example.inventory_api.domain.enums.Type;

import java.time.LocalDateTime;

public record StockMovementResponse(
        Long id,
        LocalDateTime dateTime,
        Type type,
        Integer quantity,
        String note,
        String productName,
        String userName
) {
}
