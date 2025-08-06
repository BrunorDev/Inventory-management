package com.example.inventory_api.mappers;

import com.example.inventory_api.domain.entities.Supplier;
import com.example.inventory_api.dtos.supplierDTO.SupplierRequest;
import com.example.inventory_api.dtos.supplierDTO.SupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id", ignore = true)
    Supplier toSupplier(SupplierRequest request);

    SupplierResponse toSupplierResponse(Supplier supplier);

    List<SupplierResponse> toSupplierResponseList(List<Supplier> suppliers);

}
