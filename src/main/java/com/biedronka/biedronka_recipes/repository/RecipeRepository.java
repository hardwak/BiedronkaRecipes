package com.biedronka.biedronka_recipes.repository;


import com.biedronka.biedronka_recipes.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // np. wyszukiwanie przepisu po tytule
    // List<Recipe> findByTitleContainingIgnoreCase(String titlePart);
}
