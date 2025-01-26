package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.RecipeDTO;
import com.biedronka.biedronka_recipes.dto.RecipeReducedDTO;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Multimedia;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import com.biedronka.biedronka_recipes.repository.*;
import com.biedronka.biedronka_recipes.utils.RecipeMapper;
import com.biedronka.biedronka_recipes.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("my-recipes")
public class MyRecipesController {

    private final RecipeRepository recipeRepository;
    private final ClientRepository clientRepository;
    private final RecipeMapper recipeMapper;
    private final ProductRepository productRepository;
    private final MultimediaRepository multimediaRepository;
    private final RecipeProductsRepository recipeProductsRepository;
    private RecipeService recipeService;


    public MyRecipesController(RecipeRepository recipeRepository, ClientRepository clientRepository, RecipeService recipeService, RecipeMapper recipeMapper, ProductRepository productRepository,MultimediaRepository multimediaRepository,RecipeProductsRepository recipeProductsRepository) {
        this.recipeRepository = recipeRepository;
        this.clientRepository = clientRepository;
        this.recipeMapper = recipeMapper;
        this.recipeService = recipeService;
        this.productRepository = productRepository;
        this.multimediaRepository = multimediaRepository;
        this.recipeProductsRepository = recipeProductsRepository;
    }

    @GetMapping("")
    public String getMyRecipesIndex(Model model) {
        Long clientId = 1L; // fixed
        List<Recipe> recipes = recipeRepository.findByClientIdAndIsDraft(clientId,false);
        List<Recipe> drafts  = recipeRepository.findByClientIdAndIsDraft(clientId,true);

        List<RecipeReducedDTO> recipeReducedDTOS = recipeMapper.toDtoList(recipes);
        List<RecipeDTO> draftsDTOs = recipeMapper.toDTOList(drafts);
        model.addAttribute("recipes", recipeReducedDTOS);
        model.addAttribute("drafts", draftsDTOs);
        Client client = clientRepository.getReferenceById(clientId); // tutaj jest shardcodowany client i tyle xd


        return "my-recipes";
    }

    @GetMapping("/new")
    public String newRecipeForm(Model model) {
        RecipeDTO blankRecipeDTO = new RecipeDTO(null,null,null,new ArrayList<>());
        model.addAttribute("recipe", blankRecipeDTO);
        return "recipe-form"; // Formularz dla nowego przepisu
    }


    @PostMapping("/save")
    public String saveRecipe(@ModelAttribute RecipeDTO recipeDTO,@RequestParam("isDraft") boolean isDraft) {
        Long clientId = 1L;
        recipeService.saveRecipe(recipeDTO,isDraft,clientId);
        return "redirect:/my-recipes";
    }



    @PostMapping("/edit")
    public String editDraft(@RequestParam("id") Long id, Model model) {
        // Pobranie szkicu z bazy danych na podstawie ID
        Recipe draft = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono szkicu o ID: " + id));

        // Mapowanie encji na DTO
        RecipeDTO recipeDTO = recipeMapper.toDTO(draft);
        System.out.println(recipeDTO);

        // Dodanie danych do modelu
        model.addAttribute("recipe", recipeDTO);

        // Przekierowanie do widoku formularza edycji
        return "recipe-form";
    }



}
