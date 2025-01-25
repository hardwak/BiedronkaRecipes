package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeProductsRepository extends JpaRepository<RecipeProducts, Long> {
}

