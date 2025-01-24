package com.biedronka.biedronka_recipes.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "Opinions")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stars")
    private Integer stars;

    // FK do Clients
    @ManyToOne
    @JoinColumn(name = "FK_Client")  // w oryginalnym schemacie
    private Client client;

    public Opinion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // ... get/set ...
}

