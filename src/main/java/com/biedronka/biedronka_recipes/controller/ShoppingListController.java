package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.shoppingList.ShoppingListItemResponseDTO;
import com.biedronka.biedronka_recipes.service.ShoppingListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shopping-list")
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping
    public String viewShoppingList(Model model) {
        Long clientId = 2L;
        model.addAttribute("items", shoppingListService.getActiveShoppingList(clientId));
        model.addAttribute("totalPrice", shoppingListService.calculateShoppingListPrice(clientId));
        model.addAttribute("clientId", clientId);
        return "shopping_list";
    }


    @PostMapping("/remove-item/{itemId}/{clientId}")
    public String removeItem(@PathVariable Long itemId, @PathVariable Long clientId) {
        shoppingListService.removeItem(itemId);
        return "redirect:/shopping-list";
    }

    @GetMapping("/set-promo/{itemId}")
    public String setPromo(@PathVariable Long itemId) {
        shoppingListService.applyPromo(itemId);
        return "redirect:/shopping-list";
    }

    @PostMapping("/confirm/{clientId}")
    public String confirmList(@PathVariable Long clientId, Model model) {
        try {
            List<ShoppingListItemResponseDTO> list = shoppingListService.getActiveShoppingList(clientId);
            model.addAttribute("items", shoppingListService.getActiveShoppingList(clientId));
            model.addAttribute("totalPrice", shoppingListService.calculateShoppingListPrice(clientId));
            model.addAttribute("clientId", clientId);
            if (list.isEmpty()) {
                return "confirmed_empty";
            }
            shoppingListService.confirmShoppingList(clientId);
            return "confirmed_list";
        } catch (Exception e) {
            model.addAttribute("clientId", clientId);
            return "confirmed_empty";
        }

    }
}
