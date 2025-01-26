package com.biedronka.biedronka_recipes.dto.allergen;

public record ClientAllergenResponseDTO(
        Long id,
        String name,
        Boolean isActive
) {
}
