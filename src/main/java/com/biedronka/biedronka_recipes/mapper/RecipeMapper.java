package com.biedronka.biedronka_recipes.mapper;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeResponseDTO;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import com.biedronka.biedronka_recipes.entity.RecipeRate;
import com.biedronka.biedronka_recipes.repository.RecipeProductsRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeMapper {

    private final RecipeRepository recipeRepository;
    private final RecipeProductsRepository recipeProductsRepository;
    private final RecipeProductMapper recipeProductMapper;

    public RecipeMapper(RecipeRepository recipeRepository, RecipeProductsRepository recipeProductsRepository, RecipeProductMapper recipeProductMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeProductsRepository = recipeProductsRepository;
        this.recipeProductMapper = recipeProductMapper;
    }

    public RecipeResponseDTO toResponseDto(Recipe recipe) {
        List<RecipeRate> rates = recipe.getRecipeRates();
        Double rate = 0.0;
        if (!rates.isEmpty()) {
            for (RecipeRate r : rates) {
                rate += r.getRate();
            }
            rate /= rates.size();
        }
        List<RecipeProducts> products = recipe.getRecipeProducts();
        return new RecipeResponseDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getEmployee().getFirstName(),
                rate,
                recipeProductMapper.toDtoList(products),
                recipe.getMultimedia().getUrl(),
                recipe.getDescription()
        );
    }

    public List<RecipeResponseDTO> toResponseDtoList(List<Recipe> recipes) {
        return recipes.stream().map(this::toResponseDto).collect(Collectors.toList());
    }
}
