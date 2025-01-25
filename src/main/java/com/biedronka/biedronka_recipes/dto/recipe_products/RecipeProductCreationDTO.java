package com.biedronka.biedronka_recipes.dto.recipe_products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecipeProductCreationDTO(
        @Positive Double amount,
        @NotBlank String weightUnit,
        @NotNull Long recipeId,
        @NotNull Long productId
) {
}
