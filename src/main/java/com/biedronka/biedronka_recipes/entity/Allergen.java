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
public class Allergen {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "allergens")
    private List<Product> products;

    @ManyToMany(mappedBy = "allergens")
    private List<Client> clients;
}
