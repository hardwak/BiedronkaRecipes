package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeSearchResponseDTO;
import com.biedronka.biedronka_recipes.service.RecipeService;
import com.biedronka.biedronka_recipes.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class RecipeSearchController {

    private final RecipeService recipeService;
    private final TagService tagService;

    //this used to simulate app use by one user
    private final Long clientId = 2L;

    @GetMapping
    public String search(Model model) {
        model.addAttribute("tagsByType", tagService.getAllTagsGroupedByType());
        return "searchPage";
    }

    @GetMapping(value = "/matchRecipes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecipeSearchResponseDTO>> searchRecipes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<Long> tags,
            @RequestParam(required = false) List<Double> ratings,
            @RequestParam(required = false) Boolean filterByAllergens){
        List<RecipeSearchResponseDTO> recipes = recipeService.searchRecipe(keyword, tags, ratings, filterByAllergens, clientId);
        return ResponseEntity.ok(recipes);
    }
}
