package com.example.inventory_api.dtos.productDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String sku,
        Integer quantityStock,
        LocalDate expiryDate,
        BigDecimal purchasePrice,
        BigDecimal priceOnSale,
        String categoryName,
        String supplierName
) {
}
