package com.biedronka.biedronka_recipes.dto.recipe;

import com.biedronka.biedronka_recipes.entity.Recipe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreationDTO {
        private Long id;
        @NotBlank(message = "Nazwa nie może być pusta.")
        private String name;

        @NotBlank(message = "Przebieg nie może być pusty.")
        private String description;

        @NotBlank(message = "URL zdjęcia nie może być pusty.")
        private String multimediaUrl;

        private Long employeeId;
        private List<Long> tagsIds;

        @NotEmpty(message = "Lista składników nie może być pusta.")
        private List<RecipeProductCreationDTO> recipeProducts = new ArrayList<>();

        public static RecipeCreationDTO fromEntity(Recipe recipe) {
                return RecipeCreationDTO.builder()
                        .id(recipe.getId())
                        .name(recipe.getName())
                        .description(recipe.getDescription())
                        .multimediaUrl(recipe.getMultimedia().getUrl())
                        .recipeProducts(recipe.getRecipeProducts().stream()
                                .map(RecipeProductCreationDTO::fromEntity)
                                .toList())
                        .build();
        }
}
