package com.biedronka.biedronka_recipes.entity;



import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "OpinionsRecipes")
public class OpinionRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK do Opinions
    @ManyToOne
    @JoinColumn(name = "FK_Opinia")
    private Opinion opinion;

    // FK do Recipes
    @ManyToOne
    @JoinColumn(name = "FK_Przepis")
    private Recipe recipe;

    @Column(name = "Data")
    private LocalDate date;

    public OpinionRecipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
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

