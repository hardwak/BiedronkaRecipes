package com.biedronka.biedronka_recipes.entity;

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
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    private String brand;
    private Double price;
    private Boolean promo;

    @ManyToMany
    @JoinTable(
            name = "product_alergens",
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "allergen_id")
            }
    )
    private List<Allergen> allergens;

    @OneToMany(
            mappedBy = "product"
    )
    private List<StoreroomItem> storeroomItems;

    @OneToMany(
            mappedBy = "product"
    )

    private List<RecipeProducts> productRecipes;

    @OneToMany(
            mappedBy = "product"
    )
    private List<ShoppingListItem> shoppingListItems;

    @OneToOne
    @JoinColumn(name = "multimedia_id", referencedColumnName = "id")


    private Multimedia multimedia;
}
