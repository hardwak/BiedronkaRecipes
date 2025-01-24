package com.biedronka.biedronka_recipes.controller;



import com.biedronka.biedronka_recipes.dto.CommentRecipeDTO;
import com.biedronka.biedronka_recipes.entity.CommentRecipe;
import com.biedronka.biedronka_recipes.service.CommentRecipeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment-recipe")
public class CommentRecipeController {

    private final CommentRecipeService service;

    public CommentRecipeController(CommentRecipeService service) {
        this.service = service;
    }

    @GetMapping
    public List<CommentRecipeDTO> getAll() {
        return service.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CommentRecipeDTO getOne(@PathVariable Long id) {
        var cr = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(cr);
    }

    @PostMapping
    public CommentRecipeDTO create(@RequestBody CommentRecipeDTO dto) {
        var saved = service.create(dto.getCommentId(), dto.getRecipeId(), dto.getDate());
        return toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // mapowanie
    private CommentRecipeDTO toDTO(CommentRecipe cr) {
        return new CommentRecipeDTO(
                cr.getId(),
                cr.getComment() != null ? cr.getComment().getId() : null,
                cr.getRecipe() != null ? cr.getRecipe().getId() : null,
                cr.getDate()
        );
    }
}

