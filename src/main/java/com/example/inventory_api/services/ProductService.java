package com.example.inventory_api.services;

import com.example.inventory_api.domain.entities.Product;
import com.example.inventory_api.dtos.productDTO.ProductRequest;
import com.example.inventory_api.dtos.productDTO.ProductResponse;
import com.example.inventory_api.exceptions.BusinessException;
import com.example.inventory_api.exceptions.ResourceNotFoundException;
import com.example.inventory_api.mappers.ProductMapper;
import com.example.inventory_api.repositories.CategoryRepository;
import com.example.inventory_api.repositories.ProductRepository;
import com.example.inventory_api.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Transactional
    public ProductResponse save(ProductRequest request, Long supplierId, Long categoryId) {
        if (request.quantityStock() < 0) {
            throw new BusinessException("Quantity cannot be negative");
        }

        if (productRepository.existsBySku(request.sku())) {
            throw new BusinessException("SKU is already in use, try again with a new code");
        }

        var supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        var product = mapper.toProduct(request);
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setAvailable(true);

        return mapper.toProductResponse(productRepository.save(product));
    }

    public List<ProductResponse> getAll() {
        var products = productRepository.findAll();
        return mapper.toProductResponseList(products);
    }

    public ProductResponse getById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return mapper.toProductResponse(product);
    }

    @Transactional
    public void deactivateProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getQuantityStock() > 0) {
            throw new BusinessException("Product still has quantities available in stock");
        }

        product.setAvailable(false);
    }

//    Methods QUERIES / FILTERS

    public List<Product> getByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getAllByQuantity(Integer quantity) {
        if (quantity < 0) {
            throw new BusinessException("Invalid quantity: must be 0 or more");
        }
        return productRepository.findByQuantityStockEquals(quantity);
    }

    public List<ProductResponse> getAllByCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new BusinessException("Category not found");
        }

        var products = productRepository.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new BusinessException("No products found in this category");
        }
        return mapper.toProductResponseList(products);
    }

    public List<ProductResponse> getAllBySupplier(Long supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new BusinessException("Supplier not found");
        }

        var products = productRepository.findBySupplierId(supplierId);

        if (products.isEmpty()) {
            throw new BusinessException("No products found for this supplier");
        }
        return mapper.toProductResponseList(products);
    }
}
