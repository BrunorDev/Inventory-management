package com.example.inventory_api.repositories;

import com.example.inventory_api.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findBySupplierId(Long supplierId);

    List<Product> findByName(String name);

    List<Product> findByQuantityStockEquals(Integer quantity);

    boolean existsBySku(String sku);

}
