package com.example.inventory_api.dtos.supplierDTO;

public record SupplierResponse(
        Long id,
        String name,
        String cnpj,
        String contact,
        String address
) {
}
