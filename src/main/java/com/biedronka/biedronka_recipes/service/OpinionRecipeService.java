package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.entity.Opinion;
import com.biedronka.biedronka_recipes.entity.OpinionRecipe;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.repository.OpinionRecipeRepository;
import com.biedronka.biedronka_recipes.repository.OpinionRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OpinionRecipeService {

    private final OpinionRecipeRepository opinionRecipeRepository;
    private final OpinionRepository opinionRepository;
    private final RecipeRepository recipeRepository;

    public OpinionRecipeService(OpinionRecipeRepository opinionRecipeRepository,
                                OpinionRepository opinionRepository,
                                RecipeRepository recipeRepository) {
        this.opinionRecipeRepository = opinionRecipeRepository;
        this.opinionRepository = opinionRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<OpinionRecipe> findAll() {
        return opinionRecipeRepository.findAll();
    }

    public Optional<OpinionRecipe> findById(Long id) {
        return opinionRecipeRepository.findById(id);
    }

    public OpinionRecipe create(Long opinionId, Long recipeId, LocalDate date) {
        Opinion opinion = opinionRepository.findById(opinionId)
                .orElseThrow(() -> new RuntimeException("Opinion not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        OpinionRecipe or = new OpinionRecipe();
        or.setOpinion(opinion);
        or.setRecipe(recipe);
        or.setDate(date != null ? date : LocalDate.now());
        return opinionRecipeRepository.save(or);
    }

    public void delete(Long id) {
        opinionRecipeRepository.deleteById(id);
    }
}

