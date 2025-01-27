package com.biedronka.biedronka_recipes.dto.recipe;

import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeProductCreationDTO {
    private Long productId;

    private String productName;

    @NotNull(message = "Ilość musi zostać podana.")
    @Positive(message = "Ilość musi być większa od 0.")
    private Double amount;

    @NotBlank(message = "Jednostka nie może być pusta.")
    private String weightUnit;

    public static RecipeProductCreationDTO fromEntity(RecipeProducts recipeProduct) {
        return RecipeProductCreationDTO.builder()
                .productId(recipeProduct.getProduct().getId())
                .productName(recipeProduct.getProduct().getName())
                .amount(recipeProduct.getAmount())
                .weightUnit(recipeProduct.getWeightUnit())
                .build();
    }
}
