package com.biedronka.biedronka_recipes.dto;

import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private Long id;
    private String name;
    private String description;
    private List<RecipeProductOnlyIdDTO> recipeProducts;
}
