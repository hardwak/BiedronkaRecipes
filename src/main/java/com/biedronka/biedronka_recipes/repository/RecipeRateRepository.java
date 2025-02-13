package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRateRepository extends JpaRepository<RecipeRate, Long> {
    RecipeRate findByRecipeAndClient(Recipe recipe, Client client);
}
