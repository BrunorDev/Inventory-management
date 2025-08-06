package com.example.inventory_api.dtos.productDTO;

import java.math.BigDecimal;

public record ProductSummary(
        Long id,
        String name,
        String sku,
        BigDecimal priceOnSale,
        Integer quantityStock,
        String categoryName,
        String supplierName
) {
}
