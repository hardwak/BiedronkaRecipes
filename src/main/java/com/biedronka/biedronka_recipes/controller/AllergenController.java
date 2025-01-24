package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.AllergenDTO;
import com.biedronka.biedronka_recipes.entity.Allergen;
import com.biedronka.biedronka_recipes.service.AllergenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/allergens")
public class AllergenController {

    private final AllergenService allergenService;

    public AllergenController(AllergenService allergenService) {
        this.allergenService = allergenService;
    }

    @GetMapping
    public List<AllergenDTO> getAll() {
        return allergenService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AllergenDTO getOne(@PathVariable Long id) {
        Allergen allergen = allergenService.findById(id)
                .orElseThrow(() -> new RuntimeException("Allergen not found"));
        return toDTO(allergen);
    }

    @PostMapping
    public AllergenDTO create(@RequestBody AllergenDTO dto) {
        Allergen entity = toEntity(dto);
        Allergen saved = allergenService.create(entity);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public AllergenDTO update(@PathVariable Long id, @RequestBody AllergenDTO dto) {
        Allergen existing = allergenService.findById(id)
                .orElseThrow(() -> new RuntimeException("Allergen not found"));
        existing.setName(dto.getName());
        Allergen updated = allergenService.update(existing);
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        allergenService.delete(id);
    }

    // mapowanie encja <-> dto
    private AllergenDTO toDTO(Allergen allergen) {
        return new AllergenDTO(allergen.getId(), allergen.getName());
    }

    private Allergen toEntity(AllergenDTO dto) {
        Allergen allergen = new Allergen();
        allergen.setId(dto.getId());
        allergen.setName(dto.getName());
        return allergen;
    }
}
