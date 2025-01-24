package com.biedronka.biedronka_recipes.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "ClientRecipeLikes")
public class ClientRecipeLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK do Clients
    @ManyToOne
    @JoinColumn(name = "FK_Client")
    private Client client;

    // FK do Recipes
    @ManyToOne
    @JoinColumn(name = "FK_Recipe")
    private Recipe recipe;

    public ClientRecipeLikes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    // ... get/set ...
}

