package com.example.inventory_api.dtos.productDTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductRequest(
        @NotBlank
        String name,

        String description,

        @NotBlank
        String sku,

        @PositiveOrZero
        Integer quantityStock,

        @Future
        LocalDate expiryDate,

        @Positive
        BigDecimal purchasePrice,

        @Positive
        BigDecimal priceOnSale,

        @NotNull
        Long categoryId,

        @NotNull
        Long supplierId
) {
}
