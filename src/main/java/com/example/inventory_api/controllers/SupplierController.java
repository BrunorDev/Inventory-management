package com.example.inventory_api.controllers;

import com.example.inventory_api.dtos.supplierDTO.SupplierRequest;
import com.example.inventory_api.dtos.supplierDTO.SupplierResponse;
import com.example.inventory_api.services.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponse> save(@RequestBody @Valid SupplierRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAll() {
        var suppliers = supplierService.getAll();

        if (suppliers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> update(@PathVariable Long id, @RequestBody @Valid SupplierRequest request) {
        return ResponseEntity.ok(supplierService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(Long id) {
        supplierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
