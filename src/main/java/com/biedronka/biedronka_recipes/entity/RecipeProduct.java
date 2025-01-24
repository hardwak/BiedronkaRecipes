package com.biedronka.biedronka_recipes.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "RecipesProducts")
public class RecipeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK do Recipes
    @ManyToOne
    @JoinColumn(name = "FK_Przepis")
    private Recipe recipe;

    // FK do Products
    @ManyToOne
    @JoinColumn(name = "FK_Produkt")
    private Product product;

    @Column(name = "Ilosc")
    private Double amount;

    public RecipeProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    // ... get/set ...
}

