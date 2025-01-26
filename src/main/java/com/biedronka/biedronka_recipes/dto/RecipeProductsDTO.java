package com.biedronka.biedronka_recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeProductsDTO {
    private Long id;
    private Double amount;
    private String weightUnit;
    private Long recipeId;
    private Long productId;
    private String productName;
}
