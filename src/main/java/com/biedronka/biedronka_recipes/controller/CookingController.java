package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.CookActionRequestDTO;
import com.biedronka.biedronka_recipes.dto.CookActionResponseDTO;
import com.biedronka.biedronka_recipes.service.CookingService;
import org.springframework.web.bind.annotation.*;

import static com.biedronka.biedronka_recipes.dto.CookActionRequestDTO.ActionType;

@RestController
@RequestMapping("/cooking")
public class CookingController {

    private final CookingService cookingService;

    public CookingController(CookingService cookingService) {
        this.cookingService = cookingService;
    }

    @PostMapping("/action")
    public CookActionResponseDTO handleCookAction(@RequestBody CookActionRequestDTO request) {
        switch (request.getAction()) {
            case EXECUTE:
                return cookingService.handleExecuteRecipe(request.getClientId(), request.getRecipeId());
            case LIKE:
                return cookingService.handleLikeRecipe(request.getClientId(), request.getRecipeId());
            case COMMENT:
                return cookingService.handleCommentRecipe(request.getClientId(), request.getRecipeId(),
                        request.getCommentContent());
            case OPINION:
                return cookingService.handleOpinionRecipe(request.getClientId(), request.getRecipeId(),
                        request.getOpinionStars());
            case BACK:
                return cookingService.handleBackToList();
            default:
                return new CookActionResponseDTO(false, "Nieznana akcja", null);
        }
    }
}

