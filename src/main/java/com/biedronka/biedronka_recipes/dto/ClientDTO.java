package com.biedronka.biedronka_recipes.dto;

import java.time.LocalDate;

public class ClientDTO {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate accountCreatedDate;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String email, String password,
                     String firstName, String lastName,
                     LocalDate accountCreatedDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountCreatedDate = accountCreatedDate;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getAccountCreatedDate() {
        return accountCreatedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAccountCreatedDate(LocalDate accountCreatedDate) {
        this.accountCreatedDate = accountCreatedDate;
    }
}

