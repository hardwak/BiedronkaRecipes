package com.biedronka.biedronka_recipes.dto;

public class MultimediaDTO {
    private Long id;
    private String url;
    private Integer type;
    private Long recipeId;  // jeśli w encji jest FK do Recipe

    public MultimediaDTO() {
    }

    public MultimediaDTO(Long id, String url, Integer type, Long recipeId) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.recipeId = recipeId;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Integer getType() {
        return type;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}

