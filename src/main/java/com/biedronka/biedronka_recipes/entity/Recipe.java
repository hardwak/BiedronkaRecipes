package com.biedronka.biedronka_recipes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(
            name = "employee_id"
    )
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "multimedia_id", referencedColumnName = "id")
    private Multimedia multimedia;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private List<Client> clientFavourites;

    @OneToMany(
            mappedBy = "recipe"
    )
    private List<RecipeRate> recipeRates;

    @OneToMany(
            mappedBy = "recipe"
    )
    private List<Comment> comments;

    @OneToMany(
            mappedBy = "recipe"
    )
    private List<RecipeProducts> recipeProducts;

}
