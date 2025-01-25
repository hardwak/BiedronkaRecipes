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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        List<RecipeReducedDTO> recipeReducedDTOS = recipeMapper.toDtoList(recipes);
        model.addAttribute("recipes", recipeReducedDTOS);

        Client client = clientRepository.getReferenceById(clientId); // tutaj jest shardcodowany client i tyle xd


        return "my-recipes";
    }

    @GetMapping("/new")
    public String newRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeDTO());
        return "recipe-form"; // Formularz dla nowego przepisu
    }

    @GetMapping("/drafts")
    public String draftsForm(Model model) {
        Long clientId = 1L; // Pobierz ID zalogowanego użytkownika dynamicznie
        List<Recipe> drafts = recipeRepository.findByClientIdAndIsDraft(clientId,true);

        // Mapuj encje na DTO i przekaż listę szkiców do widoku
        model.addAttribute("drafts", recipeMapper.toDtoList(drafts));
        model.addAttribute("recipe", new RecipeDTO()); // Pusty przepis na wypadek modyfikacji
        return "recipe-drafts-form"; // Formularz z listą szkiców
    }
    @PostMapping("/save")
    public String saveRecipe(@ModelAttribute RecipeDTO recipeDTO) {
        Recipe recipe = recipeMapper.toEntity(recipeDTO, productRepository);
        Long clientId = 1L;
        Client client = clientRepository.getReferenceById(clientId);
        recipe.setClient(client);
        Multimedia multimedia = Multimedia.builder()
                .url("https://example.com/images/example.jpg")
                .type("image")
                .build();
        multimediaRepository.save(multimedia);
        recipe.setMultimedia(multimedia);
        recipeRepository.save(recipe);
        for (RecipeProducts recipeProduct : recipe.getRecipeProducts()) {
            recipeProduct.setRecipe(recipe); // Ustaw powiązanie z przepisem
            recipeProductsRepository.save(recipeProduct);
        }
        return "redirect:/my-recipes";
    }

    @PostMapping("/update")
    public String updateDraft(@ModelAttribute RecipeDTO recipeDTO) {
        Recipe recipe = recipeMapper.toEntity(recipeDTO,productRepository);
        Long clientId = 1L;
        Client client = clientRepository.getReferenceById(clientId);
        recipe.setClient(client);
        Multimedia multimedia = Multimedia.builder()
                .url("https://example.com/images/example.jpg")
                .type("image")
                .build();
        multimediaRepository.save(multimedia);
        recipe.setMultimedia(multimedia);
        recipeRepository.save(recipe);
        for (RecipeProducts recipeProduct : recipe.getRecipeProducts()) {
            recipeProduct.setRecipe(recipe); // Ustaw powiązanie z przepisem
            recipeProductsRepository.save(recipeProduct);
        }

        recipeRepository.save(recipe);
        return "redirect:/my-recipes/drafts";
    }

}
