package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.ProductAllergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAllergenRepository extends JpaRepository<ProductAllergen, Long> {
}

