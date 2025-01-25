package com.biedronka.biedronka_recipes.controller;
import com.biedronka.biedronka_recipes.dto.ConfirmCookingRequestDTO;
import com.biedronka.biedronka_recipes.dto.CookingResultDTO;
import com.biedronka.biedronka_recipes.dto.CookingResultDTO;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import com.biedronka.biedronka_recipes.service.RecipeService;
import org.springframework.http.MediaType;
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

    public RecipeController(RecipeRepository recipeRepository, ClientRepository clientRepository,RecipeService recipeService) {
        this.recipeRepository = recipeRepository;
        this.clientRepository = clientRepository;
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public String getRecipeDetails(@PathVariable long id, Model model) {
        Long clientId = 1L; // fixed
        Recipe recipe = recipeRepository.getReferenceById(id);
        Client client = clientRepository.getReferenceById(clientId); // tutaj jest shardcodowany client i tyle xd
        Double averageRating = recipeService.getAverageRating(id);
        boolean liked = recipeService.hasUserLikedRecipe(id, clientId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("averageRating",averageRating);
        model.addAttribute("liked", liked);
        model.addAttribute("client", client);

        return "recipe-details";
    }
    @GetMapping("/{recipeId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long recipeId) {
        Double averageRating = recipeService.getAverageRating(recipeId);
        return ResponseEntity.ok(averageRating);
    }
    @GetMapping("/{recipeId}/liked")
    public ResponseEntity<Boolean> hasUserLikedRecipe(@PathVariable Long recipeId, @RequestParam Long clientId) {
        boolean liked = recipeService.hasUserLikedRecipe(recipeId, clientId);
        return ResponseEntity.ok(liked);
    }

    @PostMapping("/{recipeId}/like")
    public String likeRecipe(@PathVariable long recipeId,@RequestParam long clientId) {
        recipeService.likeRecipe(recipeId,clientId);
        return "redirect:/recipe/" + recipeId;
    }
    @PostMapping("/{recipeId}/unlike")
    public String unlikeRecipe(@PathVariable long recipeId,@RequestParam long clientId) {
        recipeService.unlikeRecipe(recipeId,clientId);
        return "redirect:/recipe/" + recipeId;
    }
    @PostMapping("/{recipeId}/rate")
    public String rateRecipe(@PathVariable long recipeId,@RequestParam long clientId,@RequestParam int rating) {
        recipeService.rateRecipe(recipeId,clientId,rating);
        return "redirect:/recipe/" + recipeId;
    }
    @PostMapping("/{recipeId}/comment")
    public String commentRecipe(@PathVariable long recipeId,@RequestParam long clientId,@RequestParam String comment) {
        recipeService.addComment(recipeId,clientId,comment);
        return "redirect:/recipe/" + recipeId;
    }

    @PostMapping("/{recipeId}/start")
    public ResponseEntity<CookingResultDTO> startCooking(
            @PathVariable long recipeId,
            @RequestParam long clientId) {
        System.out.println("Recipe: " + recipeId);

        CookingResultDTO result = recipeService.startCooking(recipeId, clientId);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/confirm",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> confirmCooking(
            @RequestBody ConfirmCookingRequestDTO confirmRequest) {

        System.out.println("Confirm: " + confirmRequest);
        String message = recipeService.confirmCooking(confirmRequest);
        return ResponseEntity.ok(message);
    }






}
