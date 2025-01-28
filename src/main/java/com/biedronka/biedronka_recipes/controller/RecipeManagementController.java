package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeCreationDTO;
import com.biedronka.biedronka_recipes.service.ProductService;
import com.biedronka.biedronka_recipes.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recipe-management")
public class RecipeManagementController {
    private final RecipeService recipeService;
    private final ProductService productService;

    public RecipeManagementController(RecipeService recipeService, ProductService productService) {
        this.recipeService = recipeService;
        this.productService = productService;
    }

    @GetMapping
    public String viewRecipes(Model model) {
        Long employeeId = 1L;
        model.addAttribute("employee", employeeId);
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipe_management";
    }

    @GetMapping("/edit")
    public String editRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes_edit";
    }


    @PostMapping("/edit/{id}")
    public String editRecipeForm(@PathVariable Long id, Model model) {
        RecipeCreationDTO recipeDTO = recipeService.getRecipeCreationDto(id);
        model.addAttribute("recipeDTO", recipeDTO);
        model.addAttribute("allProducts", productService.getAllProducts()); // Lista produktów do wyboru
        return "recipe_edit_form";
    }

    @PostMapping("/edit")
    public String editRecipeForm(@Valid @ModelAttribute("recipeDTO") RecipeCreationDTO recipeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeDTO", bindingResult);
            redirectAttributes.addFlashAttribute("recipeDTO", recipeDTO);
            System.out.print("----- ERROR -----");
            System.out.println(recipeDTO);
            return "redirect:/recipe-management/edit/" + recipeDTO.getId();
        }
        recipeService.updateRecipe(recipeDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Przepis został zmodyfikowany.");
        return "redirect:/recipe-management";
    }

    @GetMapping("/add")
    public String addRecipeForm(Model model) {
        model.addAttribute("recipeDTO", RecipeCreationDTO.builder().id(1L).build());
        model.addAttribute("allProducts", productService.getAllProducts());
        return "recipe_add_form";
    }

    @PostMapping("/add")
    public String addRecipe(@ModelAttribute RecipeCreationDTO recipeDTO,
                            @RequestParam("recipeProducts") String recipeProductsJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//        List<RecipeProductCreationDTO> recipeProducts = objectMapper.readValue(
//                recipeProductsJson, new TypeReference<List<RecipeProductCreationDTO>>() {}
//        );
//        System.out.println(recipeProducts);
//        System.out.println(recipeProducts.getClass());

        System.out.println(recipeProductsJson);
        System.out.println(recipeProductsJson.getClass());

        //recipeDTO.setRecipeProducts(recipeProducts);
        //recipeService.addRecipe(recipeDTO);
        return "redirect:/recipe-management";
    }


    @GetMapping("/delete")
    public String deleteRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes_delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteRecipeById(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/recipe-management/delete";
    }
}
