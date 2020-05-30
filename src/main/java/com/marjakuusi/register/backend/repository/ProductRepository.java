package com.marjakuusi.register.backend.repository;

import com.marjakuusi.register.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
