package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.OpinionRecipeDTO;
import com.biedronka.biedronka_recipes.entity.OpinionRecipe;
import com.biedronka.biedronka_recipes.service.OpinionRecipeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/opinion-recipe")
public class OpinionRecipeController {

    private final OpinionRecipeService service;

    public OpinionRecipeController(OpinionRecipeService service) {
        this.service = service;
    }

    @GetMapping
    public List<OpinionRecipeDTO> getAll() {
        return service.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OpinionRecipeDTO getOne(@PathVariable Long id) {
        OpinionRecipe or = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(or);
    }

    @PostMapping
    public OpinionRecipeDTO create(@RequestBody OpinionRecipeDTO dto) {
        OpinionRecipe saved = service.create(dto.getOpinionId(), dto.getRecipeId(), dto.getDate());
        return toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // mapowanie
    private OpinionRecipeDTO toDTO(OpinionRecipe or) {
        return new OpinionRecipeDTO(
                or.getId(),
                or.getOpinion() != null ? or.getOpinion().getId() : null,
                or.getRecipe() != null ? or.getRecipe().getId() : null,
                or.getDate()
        );
    }
}
