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
import com.example.inventory_api.validator.ProductValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final ProductValidator validator;
    private final ProductMapper mapper;

    @Transactional
    public ProductResponse save(ProductRequest request, Long supplierId, Long categoryId) {
        validator.validateSkuCode(request);

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

    @Transactional
    public ProductResponse update(Long productId, ProductRequest request) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        validator.validateBeforeUpdate(request, productId);

        product.setName(request.name());
        product.setDescription(request.description());
        product.setSku(request.sku());
        product.setQuantityStock(request.quantityStock());
        product.setExpiryDate(request.expiryDate());
        product.setPurchasePrice(request.purchasePrice());
        product.setPriceOnSale(request.priceOnSale());

        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        var supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        product.setCategory(category);
        product.setSupplier(supplier);

        return mapper.toProductResponse(product);
    }

//    Methods QUERIES / FILTERS

    public List<ProductResponse> getByName(String name) {
        return mapper.toProductResponseList(productRepository.findByName(name));
    }

    public List<ProductResponse> getAllByQuantity(Integer quantity) {
        if (quantity < 0) {
            throw new BusinessException("Invalid quantity: must be 0 or more");
        }
        return mapper.toProductResponseList(productRepository.findByQuantityStockEquals(quantity));
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
