package com.biedronka.biedronka_recipes.dto.allergen;

import jakarta.validation.constraints.NotBlank;

public record AllergenCreationDTO(
        @NotBlank String name
) {
}
