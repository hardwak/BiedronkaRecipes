package com.biedronka.biedronka_recipes.dto.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeCreationDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull Long multimediaId,
        Long employeeId,
        List<Long> tagsIds,
        List<Long> recipeProductsIds
) {
}
