package com.example.inventory_api.validator;

import com.example.inventory_api.dtos.productDTO.ProductRequest;
import com.example.inventory_api.exceptions.BusinessException;
import com.example.inventory_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    public void validateSkuCode(ProductRequest request) {
        validateProduct(request);

        if (productRepository.existsBySku(request.sku())) {
            throw new BusinessException("SKU is already in use, try again with a new code");
        }
    }

    public void validateBeforeUpdate(ProductRequest request, Long productId) {
        validateProduct(request);

        var existingProduct = productRepository.findBySku(request.sku());
        if (existingProduct.isPresent() && !existingProduct.get().getId().equals(productId)) {
            throw new BusinessException("Another product with this SKU already exists");
        }
    }

    private void validateProduct(ProductRequest request) {
        if (request.quantityStock() < 0) {
            throw new BusinessException("Quantity cannot be negative");
        }

        if (request.priceOnSale().compareTo(request.purchasePrice()) < 0) {
            throw new BusinessException("Sale price cannot be lower than purchase price");
        }

        if (request.expiryDate() != null && request.expiryDate().isBefore(LocalDate.now().plusDays(3))) {
            throw new BusinessException("Expiry date must be at least 3 days in the future");
        }
    }
}
