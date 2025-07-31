package com.example.inventory_api.mappers;

import com.example.inventory_api.domain.entities.Category;
import com.example.inventory_api.dtos.categoryDTO.CategoryRequest;
import com.example.inventory_api.dtos.categoryDTO.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}
