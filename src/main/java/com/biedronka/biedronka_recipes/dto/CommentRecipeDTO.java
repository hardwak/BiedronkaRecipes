package com.biedronka.biedronka_recipes.dto;


import java.time.LocalDate;

public class CommentRecipeDTO {
    private Long id;
    private Long commentId;
    private Long recipeId;
    private LocalDate date;

    public CommentRecipeDTO() {
    }

    public CommentRecipeDTO(Long id, Long commentId, Long recipeId, LocalDate date) {
        this.id = id;
        this.commentId = commentId;
        this.recipeId = recipeId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long getCommentId() {
        return commentId;
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

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

