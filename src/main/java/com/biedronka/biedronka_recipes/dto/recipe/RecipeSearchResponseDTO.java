package com.biedronka.biedronka_recipes.dto.recipe;

import java.util.List;

public record RecipeSearchResponseDTO(
        Long id,
        String name,
        String description,
        String employeeName,
        String multimediaUrl,
        List<String> tags
) {
}
