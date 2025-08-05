package com.example.inventory_api.dtos.supplierDTO;

import jakarta.validation.constraints.NotBlank;

public record SupplierRequest(
        @NotBlank
        String name,

        @NotBlank
        String cnpj,

        @NotBlank
        String contact,

        @NotBlank
        String address
) {
}
