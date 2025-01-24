package com.biedronka.biedronka_recipes.entity;



import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CommentRecipes")
public class CommentRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK do Comments
    @ManyToOne
    @JoinColumn(name = "FK_Comment")
    private Comment comment;

    // FK do Recipes
    @ManyToOne
    @JoinColumn(name = "FK_Recipe")
    private Recipe recipe;

    @Column(name = "Date")
    private LocalDate date;

    public CommentRecipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // ... get/set ...
}

