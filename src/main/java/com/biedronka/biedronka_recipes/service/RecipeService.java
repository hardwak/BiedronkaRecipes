package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeSearchResponseDTO;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeRate;
import com.biedronka.biedronka_recipes.entity.tags.Tag;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import com.biedronka.biedronka_recipes.utils.RecipeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public List<RecipeSearchResponseDTO> searchRecipe(
            String keyword,
            List<Long> tags,
            List<Double> rates,
            Boolean filterByClientAllergens,
            Long clientId
    ) {
        List<Recipe> matchedKeywordRecipes;
        if (keyword != null && !keyword.isEmpty())
            matchedKeywordRecipes =
                    recipeRepository.findByKeywordInNameDescriptionOrProducts(keyword);
        else
            matchedKeywordRecipes =
                    recipeRepository.findAll();

        if (matchedKeywordRecipes.isEmpty())
            return List.of();

        return matchedKeywordRecipes.stream()
                .filter(recipe -> {
                    boolean tagsMatched = (tags == null || tags.isEmpty()) || new HashSet<>(
                            recipe.getTags().stream()
                                    .map(Tag::getId)
                                    .toList()
                    ).containsAll(tags);

                    boolean ratesMatched = (rates == null || rates.isEmpty()) || rates.stream()
                            .anyMatch(rate -> rate == Math.round(
                                    recipe.getRecipeRates().stream()
                                            .mapToDouble(RecipeRate::getRate)
                                            .average()
                                            .orElse(0.0)));

                    boolean filterByClientAllergensMatched = true;
                    if (filterByClientAllergens != null && filterByClientAllergens) {
                        List<Long> clientAllergenIds = recipeRepository.findClientAllergensByClientId(clientId);
                        filterByClientAllergensMatched = recipe.getRecipeProducts().stream()
                                .noneMatch(recipeProduct -> recipeProduct.getProduct().getAllergens().stream()
                                        .anyMatch(allergen -> clientAllergenIds.contains(allergen.getId())));
                    }

                    return tagsMatched && ratesMatched && filterByClientAllergensMatched;
                })
                .map(recipeMapper::toRecipeSearchResponseDTO)
                .toList();
    }

}
