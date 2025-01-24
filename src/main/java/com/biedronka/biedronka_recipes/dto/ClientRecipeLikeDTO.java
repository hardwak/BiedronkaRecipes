package com.biedronka.biedronka_recipes.dto;

public class ClientRecipeLikeDTO {
    private Long id;
    private Long clientId;
    private Long recipeId;

    public ClientRecipeLikeDTO() {
    }

    public ClientRecipeLikeDTO(Long id, Long clientId, Long recipeId) {
        this.id = id;
        this.clientId = clientId;
        this.recipeId = recipeId;
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}
