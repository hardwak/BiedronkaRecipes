package com.biedronka.biedronka_recipes.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record ProductCreationDTO(
        @NotBlank String name,
        @NotBlank String brand,
        @NotNull @PositiveOrZero Double price,
        @NotNull Boolean promo,
        List<Long> allergensIds,
        @NotNull Long multimediaId
) {
}
