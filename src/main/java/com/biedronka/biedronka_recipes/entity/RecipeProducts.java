package com.biedronka.biedronka_recipes.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class RecipeProducts {
    @Id
    @GeneratedValue
    private Long id;
    private Double amount;
    private String weightUnit;

    @ManyToOne
    @JoinColumn(
            name = "recipe_id",
            nullable = false
    )

    private Recipe recipe;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )

    private Product product;
}
