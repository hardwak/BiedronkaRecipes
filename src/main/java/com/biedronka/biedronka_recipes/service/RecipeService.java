package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeCreationDTO;
import com.biedronka.biedronka_recipes.dto.recipe.RecipeResponseDTO;
import com.biedronka.biedronka_recipes.entity.Multimedia;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import com.biedronka.biedronka_recipes.mapper.RecipeMapper;
import com.biedronka.biedronka_recipes.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeProductsRepository recipeProductsRepository;
    private final RecipeMapper recipeMapper;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    @Autowired
    MultimediaRepository multimediaRepository;

    public RecipeService(RecipeRepository recipeRepository, RecipeProductsRepository recipeProductsRepository, RecipeMapper recipeMapper, EmployeeRepository employeeRepository, ProductRepository productRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeProductsRepository = recipeProductsRepository;
        this.recipeMapper = recipeMapper;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
    }

    public List<RecipeResponseDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipeMapper.toResponseDtoList(recipes);
    }

    public RecipeResponseDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe != null) {
            return recipeMapper.toResponseDto(recipe);
        }
        return null;
    }

    public Recipe getRecipeByID(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public RecipeCreationDTO getRecipeCreationDto(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe != null) {
            System.out.println(RecipeCreationDTO.fromEntity(recipe));
            return RecipeCreationDTO.fromEntity(recipe);
        } else {
            return new RecipeCreationDTO();
        }
    }

    @Transactional
    public void deleteRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        if (recipe.getMultimedia() != null) {
            Multimedia multimedia = recipe.getMultimedia();
            multimedia.setRecipe(null);
            recipe.setMultimedia(null);
        }

        recipe.getRecipeRates().forEach(rate -> rate.setRecipe(null));
        recipe.getRecipeRates().clear();

        recipe.getRecipeProducts().forEach(rp -> rp.setRecipe(null));
        recipe.getRecipeProducts().clear();

        recipeRepository.delete(recipe);
    }


    @Transactional
    public void updateRecipe(RecipeCreationDTO recipeDTO) {
        Recipe recipe = recipeRepository.getReferenceById(recipeDTO.getId());
        recipe.setName(recipeDTO.getName());
        recipe.setDescription(recipeDTO.getDescription());

        Multimedia multimedia = recipe.getMultimedia();
        multimedia.setUrl(recipeDTO.getMultimediaUrl());
        multimediaRepository.save(multimedia);

        recipe.getRecipeProducts().clear();
        recipeDTO.getRecipeProducts().forEach(ingredientDTO -> {
            Product product = productRepository.findById(ingredientDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produkt nie został znaleziony"));
            RecipeProducts recipeProduct = RecipeProducts.builder()
                    .product(product)
                    .recipe(recipe)
                    .amount(ingredientDTO.getAmount())
                    .weightUnit(ingredientDTO.getWeightUnit())
                    .build();
            recipe.getRecipeProducts().add(recipeProduct);
        });

        recipeRepository.save(recipe);
    }

    @Transactional
    public void addRecipe(RecipeCreationDTO recipeDTO) {
        Multimedia multimedia = Multimedia.builder()
                .url(recipeDTO.getMultimediaUrl())
                .type("image")
                .build();
        multimediaRepository.save(multimedia);

        Recipe recipe = Recipe.builder()
                .name(recipeDTO.getName())
                .description(recipeDTO.getDescription())
                .multimedia(multimedia)
                .build();

        recipeDTO.getRecipeProducts().forEach(ingredientDTO -> {
            Product product = productRepository.findById(ingredientDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produkt nie został znaleziony"));
            RecipeProducts recipeProduct = RecipeProducts.builder()
                    .product(product)
                    .recipe(recipe)
                    .amount(ingredientDTO.getAmount())
                    .weightUnit(ingredientDTO.getWeightUnit())
                    .build();
            recipe.getRecipeProducts().add(recipeProduct);
        });

        recipeRepository.save(recipe);
    }
}
