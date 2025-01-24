package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.OpinionRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionRecipeRepository extends JpaRepository<OpinionRecipe, Long> {
}

