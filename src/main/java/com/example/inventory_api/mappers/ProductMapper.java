package com.example.inventory_api.mappers;

import com.example.inventory_api.domain.entities.Product;
import com.example.inventory_api.dtos.productDTO.ProductRequest;
import com.example.inventory_api.dtos.productDTO.ProductResponse;
import com.example.inventory_api.dtos.productDTO.ProductSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "available", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "stockMovements", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductRequest request);

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toProductResponseList(List<Product> products);

    List<ProductSummary> toProductSummaryList(List<Product> products);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "supplier.name", target = "supplierName")
    ProductSummary toProductSummary(Product product);
}
