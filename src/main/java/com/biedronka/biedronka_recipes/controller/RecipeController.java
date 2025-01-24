package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.RecipeDTO;
import com.biedronka.biedronka_recipes.entity.Employee;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.service.EmployeeService;
import com.biedronka.biedronka_recipes.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final EmployeeService employeeService;

    public RecipeController(RecipeService recipeService, EmployeeService employeeService) {
        this.recipeService = recipeService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<RecipeDTO> getAll() {
        return recipeService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RecipeDTO getOne(@PathVariable Long id) {
        Recipe r = recipeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        return toDTO(r);
    }

    @PostMapping
    public RecipeDTO create(@RequestBody RecipeDTO dto) {
        Employee emp = null;
        if (dto.getEmployeeId() != null) {
            emp = employeeService.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
        }
        Recipe entity = new Recipe();
        entity.setTitle(dto.getTitle());
        entity.setProcedure(dto.getProcedure());
        entity.setEmployee(emp);

        Recipe saved = recipeService.createRecipe(entity);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public RecipeDTO update(@PathVariable Long id, @RequestBody RecipeDTO dto) {
        Recipe existing = recipeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        existing.setTitle(dto.getTitle());
        existing.setProcedure(dto.getProcedure());
        if (dto.getEmployeeId() != null) {
            Employee emp = employeeService.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            existing.setEmployee(emp);
        } else {
            existing.setEmployee(null);
        }

        Recipe updated = recipeService.updateRecipe(existing);
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }

    // mapowanie
    private RecipeDTO toDTO(Recipe r) {
        return new RecipeDTO(
                r.getId(),
                r.getTitle(),
                r.getProcedure(),
                r.getEmployee() != null ? r.getEmployee().getId() : null
        );
    }
}
