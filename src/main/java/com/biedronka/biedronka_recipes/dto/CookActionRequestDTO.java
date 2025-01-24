package com.biedronka.biedronka_recipes.dto;


public class CookActionRequestDTO {

    private Long clientId;
    private Long recipeId;
    private ActionType action;
    private String commentContent;
    private Integer opinionStars;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getOpinionStars() {
        return opinionStars;
    }

    public void setOpinionStars(Integer opinionStars) {
        this.opinionStars = opinionStars;
    }

    public enum ActionType {
        EXECUTE,  // wykonaj przepis
        LIKE,     // polub
        COMMENT,  // wystaw komentarz
        OPINION,  // wystaw opinię
        BACK      // wróć do listy
    }

    // get/set ...
}

