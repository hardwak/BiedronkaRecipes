package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.CommentRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRecipeRepository extends JpaRepository<CommentRecipe, Long> {
}

