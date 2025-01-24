package com.biedronka.biedronka_recipes.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "Multimedia")
public class Multimedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "URL")
    private String url;

    @Column(name = "Type")
    private Integer type;  // w oryginale Typ = INTEGER

    // FK do Recipes
    @ManyToOne
    @JoinColumn(name = "FK_Recipe")
    private Recipe recipe;

    public Multimedia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    // ... get/set ...
}

