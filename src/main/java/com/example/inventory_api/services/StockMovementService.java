package com.example.inventory_api.services;

import com.example.inventory_api.domain.entities.StockMovement;
import com.example.inventory_api.domain.enums.Type;
import com.example.inventory_api.dtos.stockMovementDTO.StockMovementRequest;
import com.example.inventory_api.dtos.stockMovementDTO.StockMovementResponse;
import com.example.inventory_api.exceptions.BusinessException;
import com.example.inventory_api.exceptions.ResourceNotFoundException;
import com.example.inventory_api.mappers.StockMovementMapper;
import com.example.inventory_api.repositories.ProductRepository;
import com.example.inventory_api.repositories.StockMovementRepository;
import com.example.inventory_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final StockMovementMapper mapper;

    @Transactional
    public StockMovementResponse registerSale(StockMovementRequest request, Long productId, Long userId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Integer quantity = request.quantity();

        if (quantity == null || quantity <= 0) {
            throw new BusinessException("Sale quantity must be greater than zero");
        }

        if (product.getQuantityStock() < quantity) {
            throw new BusinessException("Insufficient stock for this product");
        }

        product.setQuantityStock(product.getQuantityStock() - quantity);

        var sale = mapper.toStockMovement(request);
        sale.setUser(user);
        sale.setProduct(product);
        sale.setType(Type.OUTPUT);
        sale.setDateTime(LocalDateTime.now());

        return mapper.toStockResponse(stockMovementRepository.save(sale));
    }

    @Transactional
    public StockMovementResponse registerInput(StockMovementRequest request, Long productId, Long userId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Integer quantity = request.quantity();

        if (quantity == null || quantity <= 0) {
            throw new BusinessException("Input quantity must be greater than zero");
        }

        product.setQuantityStock(product.getQuantityStock() + quantity);

        var input = mapper.toStockMovement(request);
        input.setUser(user);
        input.setProduct(product);
        input.setType(Type.INPUT);
        input.setDateTime(LocalDateTime.now());

        return mapper.toStockResponse(stockMovementRepository.save(input));
    }


    //filter methods
    public List<StockMovementResponse> getByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new BusinessException("The product ID you entered does not exist");
        }

        var byProductId = stockMovementRepository.findByProductId(productId);
        return mapper.toStockResponseList(byProductId);
    }

    public List<StockMovementResponse> getByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new BusinessException("Start and end dates are required");
        }

        if (end.isBefore(start)) {
            throw new BusinessException("End date must be after start date");
        }

        var movements = stockMovementRepository.findByDateTimeBetween(start, end);
        return mapper.toStockResponseList(movements);
    }

    public List<StockMovementResponse> getByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("The user ID you entered does not exist");
        }

        var byUserId = stockMovementRepository.findByUserId(userId);
        return mapper.toStockResponseList(byUserId);
    }

    public List<StockMovementResponse> getByType(Type type) {
        if (type == null) {
            throw new BusinessException("Movement type must be provided");
        }

        var movements = stockMovementRepository.findByType(type);
        return mapper.toStockResponseList(movements);
    }
}
