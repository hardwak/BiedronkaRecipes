package com.biedronka.biedronka_recipes.entity;





import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "AccountCreationDate")
    private LocalDate accountCreationDate;



    // Konstruktory, gettery, settery
    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getAccountCreatedDate() {
        return accountCreationDate;
    }

    public void setAccountCreatedDate(LocalDate accountCreatedDate) {
        this.accountCreationDate = accountCreatedDate;
    }

    // ... get/set ...
}
