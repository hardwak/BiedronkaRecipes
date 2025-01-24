package com.biedronka.biedronka_recipes.entity;



import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ProductsClients")
public class ProductClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK do Products
    @ManyToOne
    @JoinColumn(name = "FK_Produkt")
    private Product product;

    // FK do Clients
    @ManyToOne
    @JoinColumn(name = "FK_Klient")
    private Client client;

    @Column(name = "ilosc")
    private Double amount;

    @Column(name = "DataOstatniegoPrzegladu")
    private LocalDate lastCheckDate;

    public ProductClient() {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(LocalDate lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    // ... get/set ...
}

