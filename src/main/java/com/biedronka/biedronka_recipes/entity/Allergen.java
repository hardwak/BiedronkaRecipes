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

    @ManyToMany
    @JoinTable(
            name = "product_alergens",
            joinColumns = {
                    @JoinColumn(name = "allergen_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "product_id")
            }
    )
    private List<Product> products;

    @ManyToMany
    @JoinTable(
            name = "client_alergens",
            joinColumns = {
                    @JoinColumn(name = "allergen_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "client_id")
            }
    )
    private List<Client> clients;
}
