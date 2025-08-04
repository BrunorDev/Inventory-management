package com.example.inventory_api.mappers;

import com.example.inventory_api.domain.entities.StockMovement;
import com.example.inventory_api.dtos.stockMovementDTO.StockMovementRequest;
import com.example.inventory_api.dtos.stockMovementDTO.StockMovementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMovementMapper {

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "quantity", ignore = true)
    @Mapping(target = "note", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "user.id", source = "userId")
    StockMovement toStockMovement(StockMovementRequest request);

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "userName", source = "user.name")
    StockMovementResponse toResponse(StockMovement stockMovement);

    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "productName", source = "product.name")
    StockMovementResponse toStockResponse(StockMovement stockMovement);

}
