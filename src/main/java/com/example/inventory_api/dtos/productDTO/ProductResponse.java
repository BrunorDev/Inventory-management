package com.example.inventory_api.dtos.productDTO;

import com.example.inventory_api.dtos.categoryDTO.CategoryResponse;
import com.example.inventory_api.dtos.supplierDTO.SupplierSummary;

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
        CategoryResponse category,
        SupplierSummary supplier
) {
}
