package com.biedronka.biedronka_recipes.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "Allergens")
public class Allergen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // Konstruktory, gettery, settery
    public Allergen() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ... get/set ...
}

