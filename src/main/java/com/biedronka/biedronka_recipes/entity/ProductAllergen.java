package com.biedronka.biedronka_recipes.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "ProductsAllergens")
public class ProductAllergen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK do Products
    @ManyToOne
    @JoinColumn(name = "FK_Product")
    private Product product;

    // FK do Allergens
    @ManyToOne
    @JoinColumn(name = "FK_Allergen")
    private Allergen allergen;

    public ProductAllergen() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Allergen getAllergen() {
        return allergen;
    }

    public void setAllergen(Allergen allergen) {
        this.allergen = allergen;
    }

    // ... get/set ...
}

