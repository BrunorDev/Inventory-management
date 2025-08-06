package com.example.inventory_api.controllers;

import com.example.inventory_api.domain.entities.Product;
import com.example.inventory_api.dtos.productDTO.ProductRequest;
import com.example.inventory_api.dtos.productDTO.ProductResponse;
import com.example.inventory_api.dtos.productDTO.ProductSummary;
import com.example.inventory_api.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/supplier/{supplierId}/category/{categoryId}")
    public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductRequest request,
                                                @PathVariable Long supplierId,
                                                @PathVariable Long categoryId) {
        var product = productService.save(request, supplierId, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductSummary>> getAll() {
        var products = productService.getAll();
        return products.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateProduct(@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter/name")
    public ResponseEntity<List<ProductSummary>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.getByName(name));
    }

    @GetMapping("/filter/quantity")
    public ResponseEntity<List<ProductSummary>> getAllByQuantity(@RequestParam Integer quantity) {
        return ResponseEntity.ok(productService.getAllByQuantity(quantity));
    }

    @GetMapping("/filter/category")
    public ResponseEntity<List<ProductSummary>> getAllByCategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok(productService.getAllByCategory(categoryId));
    }

    @GetMapping("/filter/supplier")
    public ResponseEntity<List<ProductSummary>> getAllBySupplier(@RequestParam Long supplierId) {
        return ResponseEntity.ok(productService.getAllBySupplier(supplierId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }
}
