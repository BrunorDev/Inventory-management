package com.example.inventory_api.dtos.supplierDTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record SupplierRequest(
        @NotBlank
        String name,

        @NotBlank
        @CNPJ
        String cnpj,

        @NotBlank
        String contact,

        @NotBlank
        String address
) {
}
