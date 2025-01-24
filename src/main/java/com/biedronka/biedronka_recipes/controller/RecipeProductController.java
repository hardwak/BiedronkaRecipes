package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.RecipeProductDTO;
import com.biedronka.biedronka_recipes.entity.RecipeProduct;
import com.biedronka.biedronka_recipes.service.RecipeProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipe-product")
public class RecipeProductController {

    private final RecipeProductService service;

    public RecipeProductController(RecipeProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<RecipeProductDTO> getAll() {
        return service.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RecipeProductDTO getOne(@PathVariable Long id) {
        var rp = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(rp);
    }

    @PostMapping
    public RecipeProductDTO create(@RequestBody RecipeProductDTO dto) {
        var saved = service.create(dto.getRecipeId(), dto.getProductId(), dto.getAmount());
        return toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // mapowanie
    private RecipeProductDTO toDTO(RecipeProduct rp) {
        return new RecipeProductDTO(
                rp.getId(),
                rp.getRecipe() != null ? rp.getRecipe().getId() : null,
                rp.getProduct() != null ? rp.getProduct().getId() : null,
                rp.getAmount()
        );
    }
}
