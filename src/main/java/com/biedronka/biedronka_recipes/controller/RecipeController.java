package com.biedronka.biedronka_recipes.controller;
import com.biedronka.biedronka_recipes.dto.ConfirmCookingRequestDTO;
import com.biedronka.biedronka_recipes.dto.CookingResultDTO;
import com.biedronka.biedronka_recipes.dto.CookingResultDTO;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import com.biedronka.biedronka_recipes.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final ClientRepository clientRepository;
    private  RecipeService recipeService;

    public RecipeController(RecipeRepository recipeRepository, ClientRepository clientRepository) {
        this.recipeRepository = recipeRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/{id}")
    public String getRecipeDetails(@PathVariable long id, Model model) {
        Recipe recipe = recipeRepository.getReferenceById(id);
        Client client = clientRepository.getReferenceById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("client", client);

        return "recipe-details";
    }

    @PostMapping("/{id}/like")
    public String likeRecipe(@PathVariable long recipeId,@PathVariable long clientId) {
        recipeService.likeRecipe(recipeId,clientId);
        return "redirect:/recipe/" + recipeId;
    }
    @PostMapping("/{id}/unlike")
    public String unlikeRecipe(@PathVariable long recipeId,@PathVariable long clientId) {
        recipeService.unlikeRecipe(recipeId,clientId);
        return "redirect:/recipe/" + recipeId;
    }
    @PostMapping("/{id}/rate")
    public String rateRecipe(@PathVariable long recipeId,@PathVariable long clientId,@RequestParam int rate) {
        recipeService.rateRecipe(recipeId,clientId,rate);
        return "redirect:/recipe/" + recipeId;
    }
    @PostMapping("/{id}/comment")
    public String commentRecipe(@PathVariable long recipeId,@PathVariable long clientId,@RequestParam String comment) {
        recipeService.addComment(recipeId,clientId,comment);
        return "redirect:/recipe/" + recipeId;
    }

    @PostMapping("/{recipeId}/start")
    public ResponseEntity<CookingResultDTO> startCooking(
            @PathVariable Long recipeId,
            @RequestParam Long clientId) {
        CookingResultDTO result = recipeService.startCooking(recipeId, clientId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmCooking(
            @RequestBody ConfirmCookingRequestDTO confirmRequest) {
        String message = recipeService.confirmCooking(confirmRequest);
        return ResponseEntity.ok(message);
    }






}
