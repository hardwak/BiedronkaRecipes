package com.biedronka.biedronka_recipes.dto;

import java.time.LocalDate;

public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private Double weight;
    private String unit;
    private LocalDate expirationDate;
    private Double price;
    private Boolean onSale;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String brand,
                      Double weight, String unit,
                      LocalDate expirationDate,
                      Double price, Boolean onSale) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.weight = weight;
        this.unit = unit;
        this.expirationDate = expirationDate;
        this.price = price;
        this.onSale = onSale;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Double getWeight() {
        return weight;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }
}

