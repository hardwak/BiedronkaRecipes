package com.biedronka.biedronka_recipes.dto;



public class ClientAllergyDTO {
    private Long clientId;
    private Long allergenId;

    public ClientAllergyDTO() {
    }

    public ClientAllergyDTO(Long clientId, Long allergenId) {
        this.clientId = clientId;
        this.allergenId = allergenId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getAllergenId() {
        return allergenId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setAllergenId(Long allergenId) {
        this.allergenId = allergenId;
    }
}

