package com.biedronka.biedronka_recipes.dto.recipe;

import java.util.List;

public record RecipeResponseDTO (
        Long recipeId,
        String name,
        String creatorUsername,
        Double rate,
        List<RecipeProductResponseDTO> ingredients,
        String recipeURL,
        String description
) {
}
