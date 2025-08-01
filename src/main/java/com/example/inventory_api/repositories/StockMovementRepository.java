package com.example.inventory_api.repositories;

import com.example.inventory_api.domain.entities.StockMovement;
import com.example.inventory_api.domain.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProductId(Long productId);

    List<StockMovement> findByUserId(Long userId);

    List<StockMovement> findByType(Type type);

    List<StockMovement> findByDateRange(LocalDateTime start, LocalDateTime end);
}
