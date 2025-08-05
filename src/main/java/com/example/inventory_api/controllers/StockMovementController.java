package com.example.inventory_api.controllers;

import com.example.inventory_api.domain.enums.Type;
import com.example.inventory_api.dtos.stockMovementDTO.StockMovementRequest;
import com.example.inventory_api.dtos.stockMovementDTO.StockMovementResponse;
import com.example.inventory_api.services.StockMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping("/sale/{productId}/{userId}")
    public ResponseEntity<StockMovementResponse> registerSale(
            @RequestBody @Valid StockMovementRequest request,
            @PathVariable Long productId,
            @PathVariable Long userId) {
        var response = stockMovementService.registerSale(request, productId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/input/{productId}/{userId}")
    public ResponseEntity<StockMovementResponse> registerInput(
            @RequestBody @Valid StockMovementRequest request,
            @PathVariable Long productId,
            @PathVariable Long userId) {
        var response = stockMovementService.registerInput(request, productId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<StockMovementResponse>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(stockMovementService.getByDateRange(start, end));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<StockMovementResponse>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(stockMovementService.getByUserId(userId));
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<StockMovementResponse>> getByType(@RequestParam Type type) {
        return ResponseEntity.ok(stockMovementService.getByType(type));
    }

    @GetMapping
    public ResponseEntity<List<StockMovementResponse>> getAll() {
        var movements = stockMovementService.getAll();
        return movements.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(movements);
    }
}
