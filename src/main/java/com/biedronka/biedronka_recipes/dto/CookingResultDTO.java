package com.biedronka.biedronka_recipes.dto;

import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CookingResultDTO {
    private boolean success;
    private HashMap<RecipeProducts,Double> missingProducts;
    private String message;
}

