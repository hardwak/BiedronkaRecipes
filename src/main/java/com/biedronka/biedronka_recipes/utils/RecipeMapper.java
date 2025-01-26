package com.biedronka.biedronka_recipes.utils;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeSearchResponseDTO;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeMapper {

    public RecipeSearchResponseDTO toRecipeSearchResponseDTO(Recipe recipe) {
        return new RecipeSearchResponseDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getEmployee().getFirstName() + " " + recipe.getEmployee().getLastName(),
                recipe.getMultimedia().getUrl(),
                recipe.getTags().stream().map((Tag::getName)).toList()
        );
    }
}
