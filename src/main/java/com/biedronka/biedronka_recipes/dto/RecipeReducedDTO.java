package com.biedronka.biedronka_recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeReducedDTO {
    private Long id;
    private String name;

}
