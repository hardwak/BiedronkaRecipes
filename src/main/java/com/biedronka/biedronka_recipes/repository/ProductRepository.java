package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

