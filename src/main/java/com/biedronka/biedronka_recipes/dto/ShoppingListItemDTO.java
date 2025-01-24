package com.biedronka.biedronka_recipes.dto;

public class ShoppingListItemDTO {
    private Long id;
    private String productName;
    private Double quantity;
    private String unit;
    private boolean promotional;
    private Long ownerId;

    public ShoppingListItemDTO() {
    }

    public ShoppingListItemDTO(Long id, String productName, Double quantity,
                               String unit, boolean promotional, Long ownerId) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unit = unit;
        this.promotional = promotional;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isPromotional() {
        return promotional;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPromotional(boolean promotional) {
        this.promotional = promotional;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}

