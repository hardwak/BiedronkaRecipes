package com.biedronka.biedronka_recipes.dto;

import java.time.LocalDate;

public class ProductClientDTO {
    private Long id;
    private Long productId;
    private Long clientId;
    private Double amount;
    private LocalDate lastCheckDate;

    public ProductClientDTO() {
    }

    public ProductClientDTO(Long id, Long productId, Long clientId, Double amount, LocalDate lastCheckDate) {
        this.id = id;
        this.productId = productId;
        this.clientId = clientId;
        this.amount = amount;
        this.lastCheckDate = lastCheckDate;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getLastCheckDate() {
        return lastCheckDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setLastCheckDate(LocalDate lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }
}

