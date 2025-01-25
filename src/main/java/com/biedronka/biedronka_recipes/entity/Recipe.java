package com.biedronka.biedronka_recipes.entity;

import com.biedronka.biedronka_recipes.entity.tags.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
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

    @ManyToMany
    @JoinTable(
            name = "recipe_tags",
            joinColumns = {
                    @JoinColumn(name = "recipe_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id")
            }
    )
    private List<Tag> tags;

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
