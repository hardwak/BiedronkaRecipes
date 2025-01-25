package com.biedronka.biedronka_recipes.dto.tag;

import jakarta.validation.constraints.NotBlank;

public record TagCreationDTO(
        @NotBlank String name
) {
}
