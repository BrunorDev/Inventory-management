package com.example.inventory_api.services;

import com.example.inventory_api.domain.entities.Category;
import com.example.inventory_api.dtos.categoryDTO.CategoryRequest;
import com.example.inventory_api.dtos.categoryDTO.CategoryResponse;
import com.example.inventory_api.exceptions.BusinessException;
import com.example.inventory_api.exceptions.ResourceNotFoundException;
import com.example.inventory_api.mappers.CategoryMapper;
import com.example.inventory_api.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Transactional
    public CategoryResponse save(CategoryRequest request) {
        var category = mapper.toCategory(request);
        return mapper.toCategoryResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getAll() {
        var categories = categoryRepository.findAll();
        return mapper.toCategoryResponseList(categories);
    }

    public CategoryResponse getById(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return mapper.toCategoryResponse(category);
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        category.setName(request.name());
        return mapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new BusinessException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
