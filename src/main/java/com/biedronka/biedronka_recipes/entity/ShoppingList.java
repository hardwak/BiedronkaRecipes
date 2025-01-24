package com.biedronka.biedronka_recipes.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Encja odwzorowująca tabele ShoppingLists.
 */
@Entity
@Table(name = "ShoppingLists")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kolumna FK_Client -> referencja do tabeli Clients
    @ManyToOne
    @JoinColumn(name = "FK_Client")
    private Client client;

    // Kolumna FK_Product -> referencja do tabeli Products
    @ManyToOne
    @JoinColumn(name = "FK_Product")
    private Product product;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "FinalPrice")
    private Double finalPrice;

    @Column(name = "ConfirmationDate")
    private LocalDate confirmationDate;

    // Konstruktor bezargumentowy (wymagany przez JPA)
    public ShoppingList() {
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public LocalDate getConfirmationDate() {
        return confirmationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setConfirmationDate(LocalDate confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
}
