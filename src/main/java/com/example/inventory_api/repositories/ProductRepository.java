package com.example.inventory_api.repositories;

import com.example.inventory_api.domain.entities.Category;
import com.example.inventory_api.domain.entities.Product;
import com.example.inventory_api.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    List<Product> findBySupplier(Supplier supplier);

    Optional<Product> findByName(String name);

    Product findByQuantityStockEquals(Integer quantity);

    boolean existsBySku(String sku);

}
