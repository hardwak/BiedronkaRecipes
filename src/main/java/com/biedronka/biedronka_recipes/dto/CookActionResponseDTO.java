package com.biedronka.biedronka_recipes.dto;

import com.biedronka.biedronka_recipes.entity.Product;

import java.util.HashMap;
import java.util.List;

public class CookActionResponseDTO {

    private boolean success;
    private String message;             // np. "Przepis wykonany" / "Brakuje składników"
    private HashMap<Product,Integer> missingProducts; // lista ID brakujących produktów (opcjonalnie)

    public CookActionResponseDTO() {
    }

    public CookActionResponseDTO(boolean success, String message, HashMap<Product,Integer> missingProducts) {
        this.success = success;
        this.message = message;
        this.missingProducts = missingProducts;
    }

    // get/set ...
}
