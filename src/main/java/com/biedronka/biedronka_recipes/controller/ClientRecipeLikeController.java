package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.ClientRecipeLikeDTO;
import com.biedronka.biedronka_recipes.entity.ClientRecipeLikes;
import com.biedronka.biedronka_recipes.service.ClientRecipeLikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client-recipe-likes")
public class ClientRecipeLikeController {

    private final ClientRecipeLikeService service;

    public ClientRecipeLikeController(ClientRecipeLikeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClientRecipeLikeDTO> getAll() {
        return service.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientRecipeLikeDTO getOne(@PathVariable Long id) {
        ClientRecipeLikes crl = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(crl);
    }

    @PostMapping
    public ClientRecipeLikeDTO create(@RequestBody ClientRecipeLikeDTO dto) {
        ClientRecipeLikes saved = service.create(dto.getClientId(), dto.getRecipeId());
        return toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // mapowanie
    private ClientRecipeLikeDTO toDTO(ClientRecipeLikes entity) {
        return new ClientRecipeLikeDTO(
                entity.getId(),
                entity.getClient() != null ? entity.getClient().getId() : null,
                entity.getRecipe() != null ? entity.getRecipe().getId() : null
        );
    }
}

