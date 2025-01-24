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
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    private String brand;
    private Double price;
//    private LocalDate expiryDate; //Makes no sense??
//    private Boolean promo;

    @ManyToMany(mappedBy = "products")
    private List<Allergen> allergens;

    @OneToMany(
            mappedBy = "product"
    )
    private List<ShoppingListItem> shoppingListItems;

    @OneToMany(
            mappedBy = "product"
    )
    private List<RecipeProducts> productRecipes;
}
