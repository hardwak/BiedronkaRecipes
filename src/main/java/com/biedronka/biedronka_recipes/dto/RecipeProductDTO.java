package com.biedronka.biedronka_recipes.dto;

public class RecipeProductDTO {
    private Long id;
    private Long recipeId;
    private Long productId;
    private Double amount;

    public RecipeProductDTO() {
    }

    public RecipeProductDTO(Long id, Long recipeId, Long productId, Double amount) {
        this.id = id;
        this.recipeId = recipeId;
        this.productId = productId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public Long getProductId() {
        return productId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

