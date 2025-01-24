package com.biedronka.biedronka_recipes.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "ClientAllergies")
public class ClientAllergy {

    @EmbeddedId
    private ClientAllergyId id;

    // Powiązanie z tabelą Clients
    @MapsId("clientId")
    @ManyToOne
    @JoinColumn(name = "FK_Klient")
    private Client client;

    // Powiązanie z tabelą Allergens
    @MapsId("allergenId")
    @ManyToOne
    @JoinColumn(name = "FK_Alergen")
    private Allergen allergen;

    public ClientAllergy() {
    }

    public ClientAllergy(Client client, Allergen allergen) {
        this.client = client;
        this.allergen = allergen;
        this.id = new ClientAllergyId(client.getId(), allergen.getId());
    }

    // get/set ...
}

