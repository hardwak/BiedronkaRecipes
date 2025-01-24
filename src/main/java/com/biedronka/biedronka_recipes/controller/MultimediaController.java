package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.MultimediaDTO;
import com.biedronka.biedronka_recipes.entity.Multimedia;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.service.MultimediaService;
import com.biedronka.biedronka_recipes.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/multimedia")
public class MultimediaController {

    private final MultimediaService multimediaService;
    private final RecipeService recipeService;

    public MultimediaController(MultimediaService multimediaService, RecipeService recipeService) {
        this.multimediaService = multimediaService;
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<MultimediaDTO> getAll() {
        return multimediaService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MultimediaDTO getOne(@PathVariable Long id) {
        Multimedia m = multimediaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Multimedia not found"));
        return toDTO(m);
    }

    @PostMapping
    public MultimediaDTO create(@RequestBody MultimediaDTO dto) {
        Recipe recipe = null;
        if (dto.getRecipeId() != null) {
            recipe = recipeService.findById(dto.getRecipeId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));
        }
        Multimedia entity = new Multimedia();
        entity.setUrl(dto.getUrl());
        entity.setType(dto.getType());
        entity.setRecipe(recipe);

        Multimedia saved = multimediaService.createMultimedia(entity);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public MultimediaDTO update(@PathVariable Long id, @RequestBody MultimediaDTO dto) {
        Multimedia existing = multimediaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Multimedia not found"));
        existing.setUrl(dto.getUrl());
        existing.setType(dto.getType());
        if (dto.getRecipeId() != null) {
            Recipe recipe = recipeService.findById(dto.getRecipeId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));
            existing.setRecipe(recipe);
        } else {
            existing.setRecipe(null);
        }
        Multimedia updated = multimediaService.updateMultimedia(existing);
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        multimediaService.deleteMultimedia(id);
    }

    // mapowanie
    private MultimediaDTO toDTO(Multimedia m) {
        return new MultimediaDTO(
                m.getId(),
                m.getUrl(),
                m.getType(),
                m.getRecipe() != null ? m.getRecipe().getId() : null
        );
    }
}
