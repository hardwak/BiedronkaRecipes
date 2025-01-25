package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT AVG(r.rate) FROM RecipeRate r WHERE r.recipe.id = :recipeId")
    Double findAverageRatingByRecipeId(@Param("recipeId") Long recipeId);


    List<Recipe> findByClientIdAndIsDraft(Long clientId, Boolean isDraft);
    List<Recipe> findByClientId(Long clientId);

    @Query(value = "SELECT recipe.nextval FROM dual", nativeQuery =
            true)
    Long getNextId();

}


