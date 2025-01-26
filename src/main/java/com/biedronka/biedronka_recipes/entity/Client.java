package com.biedronka.biedronka_recipes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    @Column(
            unique = true,
            nullable = false
    )
    private String email;
    private String password;
    private LocalDate creationDate;

    @ManyToMany
    @JoinTable(
            name = "client_alergens",
            joinColumns = {
                    @JoinColumn(name = "client_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "allergen_id")
            }
    )
    private List<Allergen> allergens;

    @ManyToMany
    @JoinTable(
            name = "client_favourites",
            joinColumns = {
                    @JoinColumn(name = "client_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "recipe_id")
            }
    )
    private List<Recipe> favoriteRecipes;

    @OneToMany(
            mappedBy = "client"
    )
    private List<StoreroomItem> storeroomItems;

    @OneToMany(
            mappedBy = "client"
    )
    private List<ShoppingListItem> shoppingListItems;

    @OneToMany(
            mappedBy = "client"
    )
    private List<RecipeRate> recipeRates;

    @OneToMany(
            mappedBy = "client"
    )
    private List<Comment> comments;
}
