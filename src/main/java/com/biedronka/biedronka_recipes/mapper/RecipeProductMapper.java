package com.biedronka.biedronka_recipes.mapper;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeProductResponseDTO;
import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import com.biedronka.biedronka_recipes.repository.RecipeProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeProductMapper {
    private final RecipeProductsRepository recipeProductsRepository;

    public RecipeProductMapper(RecipeProductsRepository recipeProductsRepository) {
        this.recipeProductsRepository = recipeProductsRepository;
    }

    public RecipeProductResponseDTO toDto(RecipeProducts recipeProducts) {
        return new RecipeProductResponseDTO(
                recipeProducts.getId(),
                recipeProducts.getProduct().getId(),
                recipeProducts.getProduct().getName(),
                recipeProducts.getAmount(),
                recipeProducts.getWeightUnit()
        );
    }

    public List<RecipeProductResponseDTO> toDtoList(List<RecipeProducts> recipeProductsList) {
        return  recipeProductsList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
