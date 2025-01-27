package com.biedronka.biedronka_recipes.utils;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeResponseDTO;
import com.biedronka.biedronka_recipes.dto.recipe.RecipeSearchResponseDTO;
import com.biedronka.biedronka_recipes.dto.RecipeDTO;
import com.biedronka.biedronka_recipes.dto.RecipeProductOnlyIdDTO;
import com.biedronka.biedronka_recipes.dto.RecipeReducedDTO;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeRate;
import com.biedronka.biedronka_recipes.entity.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import com.biedronka.biedronka_recipes.utils.RecipeProductMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeMapper {

    private final RecipeProductMapper recipeProductMapper;

    public RecipeSearchResponseDTO toRecipeSearchResponseDTO(Recipe recipe) {
        return new RecipeSearchResponseDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getEmployee().getFirstName() + " " + recipe.getEmployee().getLastName(),
                recipe.getMultimedia().getUrl(),
                recipe.getTags().stream().map((Tag::getName)).toList()
        );
    }

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

    public RecipeDTO toDTO(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        List<RecipeProductOnlyIdDTO> recipeProductOnlyIdDTOList = RecipeProductMapper.mapToDtoList(recipe.getRecipeProducts());
        return new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipeProductOnlyIdDTOList
        );
    }

    public List<RecipeDTO> toDTOList(List<Recipe> recipes) {
        if (recipes == null || recipes.isEmpty()) {
            return List.of();
        }

        return recipes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
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

