package com.biedronka.biedronka_recipes.dto.recipe;

public record RecipeProductResponseDTO(
        Long id,
        Long productId,
        String productName,
        Double amount,
        String weightUnit
){
}
