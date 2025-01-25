package com.biedronka.biedronka_recipes.dto.multimedia;

import jakarta.validation.constraints.NotBlank;

public record MultimediaCreationDTO(
        @NotBlank String url,
        @NotBlank String type
) {
}
