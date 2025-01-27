package com.biedronka.biedronka_recipes.dto;

import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissingProductDTO {
    private RecipeProductsDTO recipeProductsDTO;
    private Double missingAmount;
}
