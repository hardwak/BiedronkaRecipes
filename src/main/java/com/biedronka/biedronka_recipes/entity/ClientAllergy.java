package com.biedronka.biedronka_recipes.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "ClientAllergies")
public class ClientAllergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Powiązanie z tabelą Clients
    @MapsId("clientId")
    @ManyToOne
    @JoinColumn(name = "FK_Client")
    private Client client;

    // Powiązanie z tabelą Allergens
    @MapsId("allergenId")
    @ManyToOne
    @JoinColumn(name = "FK_Allergen")
    private Allergen allergen;

    public ClientAllergy() {
    }

    public ClientAllergy(Client client, Allergen allergen) {
        this.client = client;
        this.allergen = allergen;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Allergen getAllergen() {
        return allergen;
    }

    public void setAllergen(Allergen allergen) {
        this.allergen = allergen;
    }

    // get/set ...
}

