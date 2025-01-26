package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT DISTINCT recipe FROM Recipe recipe " +
            "LEFT JOIN recipe.recipeProducts recipeProduct " +
            "LEFT JOIN recipeProduct.product product " +
            "WHERE LOWER(recipe.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(recipe.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(product.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Recipe> findByKeywordInNameDescriptionOrProducts(@Param("keyword") String keyword);

    @Query("SELECT a.id FROM Allergen a JOIN a.clients c WHERE c.id = :clientId")
    List<Long> findClientAllergensByClientId(@Param("clientId") Long clientId);

}
