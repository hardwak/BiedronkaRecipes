package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.RecipeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeProductRepository extends JpaRepository<RecipeProduct, Long> {
}

