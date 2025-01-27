package com.biedronka.biedronka_recipes;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeSearchResponseDTO;
import com.biedronka.biedronka_recipes.entity.Allergen;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.RecipeRate;
import com.biedronka.biedronka_recipes.entity.tags.Tag;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import com.biedronka.biedronka_recipes.repository.TagRepository;
import com.biedronka.biedronka_recipes.service.RecipeService;
import com.biedronka.biedronka_recipes.utils.RecipeMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Objects;


@SpringBootTest
class SearchRecipesTests {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TagRepository tagRepository;

    @Test
    @Transactional
    @Rollback
    void searchAllRecipes() {
        List<RecipeSearchResponseDTO> expected = recipeRepository.findAll()
                .stream()
                .filter(recipe -> !recipe.getIsDraft())
                .map(recipeMapper::toRecipeSearchResponseDTO)
                .toList();

        List<RecipeSearchResponseDTO> result = recipeService.searchRecipe(null, null, null, false, 1L);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @Transactional
    @Rollback
    void searchAllRecipesExceptClientAllergens() {
        Long clientId = 1L;
        Client client = clientRepository.findById(clientId).orElse(null);

        Assertions.assertNotNull(client);

        List<Allergen> clientAllergens = client.getAllergens();

        List<Allergen> allergensOfCollectedRecipes =
                recipeRepository.findAllById(
                                recipeService.searchRecipe(null, null, null, true, 1L).stream()
                                        .map(RecipeSearchResponseDTO::id)
                                        .toList()
                        ).stream()
                        .flatMap(recipe -> recipe.getRecipeProducts().stream()
                                .flatMap(recipeProducts -> recipeProducts.getProduct().getAllergens().stream())
                        )
                        .distinct()
                        .toList();

        Assertions.assertTrue(allergensOfCollectedRecipes.stream().noneMatch(clientAllergens::contains));
    }

    @Test
    @Transactional
    @Rollback
    void searchKeywordRecipesWithMatch() {
        String keyword = "chicken";

        List<RecipeSearchResponseDTO> expected = recipeRepository.findAll()
                .stream()
                .filter(recipe -> {
                    boolean nameMatch = recipe.getName().toLowerCase().contains(keyword);
                    boolean descriptionMatch = recipe.getDescription().toLowerCase().contains(keyword);
                    boolean productMatch = recipe.getRecipeProducts().stream()
                            .anyMatch(recipeProducts -> recipeProducts.getProduct().getName().toLowerCase().contains(keyword));

                    return nameMatch || descriptionMatch || productMatch;
                })
                .map(recipeMapper::toRecipeSearchResponseDTO)
                .toList();

        List<RecipeSearchResponseDTO> result = recipeService.searchRecipe(keyword, null, null, false, 1L);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @Transactional
    @Rollback
    void searchKeywordRecipesNoMatch() {
        String keyword = "dashfgjhkasgfdhjgsahjkferwageartvbdswfdsaveqarR78906BD123Q3";

        List<RecipeSearchResponseDTO> expected = List.of();

        List<RecipeSearchResponseDTO> result = recipeService.searchRecipe(keyword, null, null, false, 1L);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @Transactional
    @Rollback
    void searchAllRecipesWithTags() {
        //11 - Polish, 17 - 30+ min
        List<Tag> tags = List.of(
                Objects.requireNonNull(tagRepository.findById(11L).orElse(null)),
                Objects.requireNonNull(tagRepository.findById(17L).orElse(null))
        );

        List<RecipeSearchResponseDTO> result = recipeService.searchRecipe(
                null, tags.stream().map(Tag::getId).toList(), null, false, 1L
        );

        Assertions.assertTrue(
                recipeRepository.findAllById(
                                result.stream().map(RecipeSearchResponseDTO::id).toList()
                        )
                        .stream()
                        .allMatch(recipe -> recipe.getTags().containsAll(tags))
        );
    }

    @Test
    @Transactional
    @Rollback
    void searchAllRecipesWithRatings() {
        List<Double> ratings = List.of(5.0, 2.0);

        List<RecipeSearchResponseDTO> result = recipeService.searchRecipe(
                null, null, ratings, false, 1L
        );

        Assertions.assertTrue(
                recipeRepository.findAllById(
                                result.stream().map(RecipeSearchResponseDTO::id).toList()
                        )
                        .stream()
                        .allMatch(recipe -> {
                            double recipeRating = Math.round(
                                    recipe.getRecipeRates().stream()
                                            .mapToDouble(RecipeRate::getRate)
                                            .average()
                                            .orElse(0.0)
                            );
                            return ratings.contains(recipeRating);
                        })
        );
    }

}

