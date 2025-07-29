package com.example.inventory_api.repositories;

import com.example.inventory_api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByActiveTrue();

    Optional<User> findByIdAndActiveTrue(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
