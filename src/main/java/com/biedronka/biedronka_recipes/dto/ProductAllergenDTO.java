package com.biedronka.biedronka_recipes.dto;

public class ProductAllergenDTO {
    private Long id;
    private Long productId;
    private Long allergenId;

    public ProductAllergenDTO() {
    }

    public ProductAllergenDTO(Long id, Long productId, Long allergenId) {
        this.id = id;
        this.productId = productId;
        this.allergenId = allergenId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getAllergenId() {
        return allergenId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setAllergenId(Long allergenId) {
        this.allergenId = allergenId;
    }
}
