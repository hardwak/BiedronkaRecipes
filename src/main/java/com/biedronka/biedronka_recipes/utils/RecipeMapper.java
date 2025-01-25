package com.biedronka.biedronka_recipes.utils;

import com.biedronka.biedronka_recipes.dto.RecipeDTO;
import com.biedronka.biedronka_recipes.dto.RecipeProductOnlyIdDTO;
import com.biedronka.biedronka_recipes.dto.RecipeReducedDTO;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {

    // Mapowanie pojedynczego obiektu Recipe na RecipeDTO
    public RecipeReducedDTO toDto(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        return new RecipeReducedDTO(
                recipe.getId(),
                recipe.getName()
                 // Dodaj inne pola, jeśli są wymagane
        );
    }

    // Mapowanie listy Recipe na listę RecipeDTO
    public List<RecipeReducedDTO> toDtoList(List<Recipe> recipes) {
        if (recipes == null || recipes.isEmpty()) {
            return List.of();
        }

        return recipes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Recipe toEntity(RecipeDTO dto, ProductRepository productRepository) {
        if (dto == null) {
            return null;
        }
        System.out.println("dto: " + dto);
        List<RecipeProductOnlyIdDTO> recipeProductsDTO = dto.getRecipeProducts();
        System.out.println("recipeProductsDTO: " + recipeProductsDTO);
        List<RecipeProducts> recipeProducts = RecipeProductMapper.mapToEntityList(recipeProductsDTO,productRepository);



        Recipe recipe = Recipe.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .recipeProducts(recipeProducts)
                .build();

        return recipe;

    }
}

