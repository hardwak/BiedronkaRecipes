package com.biedronka.biedronka_recipes.dto;

import java.time.LocalDate;

public class OpinionRecipeDTO {
    private Long id;
    private Long opinionId;
    private Long recipeId;
    private LocalDate date;

    public OpinionRecipeDTO() {
    }

    public OpinionRecipeDTO(Long id, Long opinionId, Long recipeId, LocalDate date) {
        this.id = id;
        this.opinionId = opinionId;
        this.recipeId = recipeId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long getOpinionId() {
        return opinionId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOpinionId(Long opinionId) {
        this.opinionId = opinionId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
