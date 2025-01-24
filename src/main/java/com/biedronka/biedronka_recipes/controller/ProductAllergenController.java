package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.ProductAllergenDTO;
import com.biedronka.biedronka_recipes.entity.ProductAllergen;
import com.biedronka.biedronka_recipes.service.ProductAllergenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product-allergens")
public class ProductAllergenController {

    private final ProductAllergenService service;

    public ProductAllergenController(ProductAllergenService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductAllergenDTO> getAll() {
        return service.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductAllergenDTO getOne(@PathVariable Long id) {
        var pa = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(pa);
    }

    @PostMapping
    public ProductAllergenDTO create(@RequestBody ProductAllergenDTO dto) {
        var saved = service.create(dto.getProductId(), dto.getAllergenId());
        return toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // mapowanie
    private ProductAllergenDTO toDTO(ProductAllergen pa) {
        return new ProductAllergenDTO(
                pa.getId(),
                pa.getProduct() != null ? pa.getProduct().getId() : null,
                pa.getAllergen() != null ? pa.getAllergen().getId() : null
        );
    }
}

