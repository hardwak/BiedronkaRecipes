package com.biedronka.biedronka_recipes.dto;

import java.time.LocalDate;

/**
 * DTO do transferu danych ShoppingList.
 */
public class ShoppingListDTO {
    private Long id;
    private Long clientId;
    private Long productId;
    private Integer quantity;
    private Double finalPrice;
    private LocalDate confirmationDate;

    public ShoppingListDTO() {
    }

    public ShoppingListDTO(Long id, Long clientId, Long productId,
                           Integer quantity, Double finalPrice,
                           LocalDate confirmationDate) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
        this.confirmationDate = confirmationDate;
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getProductId() {
        return productId;
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

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

