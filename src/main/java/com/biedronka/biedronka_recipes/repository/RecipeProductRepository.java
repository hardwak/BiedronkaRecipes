package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.RecipeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeProductRepository extends JpaRepository<RecipeProduct, Long> {

    List<RecipeProduct> findByRecipeId(Long recipeId);


}

