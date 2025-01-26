package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.allergen.ClientAllergenResponseDTO;
import com.biedronka.biedronka_recipes.service.AllergenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my-allergens")
public class MyAllergensController {
    private final AllergenService allergenService;

    //this used to simulate app use by one user
    private final Long clientId = 1L;

    @GetMapping
    public String showAllergenPreferences(Model model) {
        List<ClientAllergenResponseDTO> allAllergens = allergenService.getAllMyAllergens(clientId);

        model.addAttribute("client", clientId);
        model.addAttribute("allAllergens", allAllergens);

        return "allergenPreferences";
    }

    @PostMapping("/update")
    public String updateAllergenPreferences(
            @RequestParam(name = "allergenIds", required = false) List<Long> allergenIds,
            RedirectAttributes redirectAttributes
    ) {
        try {
            List<ClientAllergenResponseDTO> updatedAllergens = allergenService.updateMyAllergens(clientId, allergenIds);
            redirectAttributes.addFlashAttribute("successMessage", "Preferences successfully updated!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No changes have been made.");
        }

        return "redirect:/my-allergens";
    }
}
